package com.lti.controller;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lti.dto.AadharDto;
import com.lti.dto.ApprovalStatusDto;
import com.lti.dto.BeneficiaryDto;
import com.lti.dto.CustomerDto;
import com.lti.dto.CustomerWithAddressDto;
import com.lti.dto.ForgetPassword;
import com.lti.dto.LoginDto;
import com.lti.dto.LoginStatus;
import com.lti.dto.RegisterDto;
import com.lti.dto.StatementDto;
import com.lti.dto.Status;
import com.lti.dto.Status.StatusType;
import com.lti.dto.TransactionDto;
import com.lti.dto.panCardDto;
import com.lti.entity.Account;
import com.lti.entity.AccountType;
import com.lti.entity.Address;
import com.lti.entity.ApprovalStatus;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Register;
import com.lti.entity.Transaction;
import com.lti.service.AccountService;
import com.lti.service.CustomerService;
import com.lti.service.EmailService;
import com.lti.service.TransactionService;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BankController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	TransactionService transactionService;
	
	@Autowired
	EmailService emailService;
	
	@PostMapping("/addOrUpdateACustomer")
	//http://localhost:8585/addOrUpdateACustomer
	public Status addorUpdateCustomer(@RequestBody CustomerDto customerDto) {
		try {
			 Customer customer=new Customer();
		        customer.setFirstName(customerDto.getFirstName());
		        customer.setMiddleName(customerDto.getMiddleName());
		        customer.setLastName(customerDto.getLastName());
		        customer.setMobile(customerDto.getMobile());
		        customer.setEmail(customerDto.getEmail());
		        customer.setAadharNo(customerDto.getAadharNo());
		        customer.setDateOfBirth(LocalDate.parse(customerDto.getDateOfBirth()));
		        customer.setPanCard(customerDto.getPanCard());
		        customer.setOccupationType(customerDto.getOccupationType());
		        customer.setAnnualIncome(customerDto.getAnnualIncome());
		        
		        int max = 500000;
		        int min = 100000;
		        int b = (int)(Math.random()*(max-min+1)+min); 
		        customer.setPassword(b);
             customerService.addOrUpdateACustomer(customer);
             System.out.println(customer.getPassword());
             //return customer;
             
             Status status = new Status();
             status.setStatus(StatusType.SUCCESS);
             status.setMessage("Registration successful!");
             return status;
         }
         catch(Exception e) {
             Status status = new Status();
             status.setStatus(StatusType.FAILURE);
             status.setMessage(e.getMessage());
             return status;
         }
	}
	
	@PostMapping("/addAddressToACustomer")
	//http://localhost:8585/addAddressToACustomer
	public String addAddressToACustomer(@RequestBody Address address) {
			int custid = customerService.findLastAddedCustomer();
			Customer c = customerService.findCustomerById(custid);
			address.setCustomer(c);
			customerService.addAddressToACustomer(address);
			return null;
	}
	

	@GetMapping("/viewAllCustomer")
	//http://localhost:8585/viewAllCustomer
	public List<Customer> viewAllCustomer(HttpServletRequest request) {
		List<Customer> customers = customerService.viewAllCustomer();
		return customers;
	}

	
	
	@GetMapping("/approvalVerification")
	//http://localhost:8585/approvalVerification
	public ApprovalStatusDto approvalVerification(@RequestParam("custId") int custid, HttpServletRequest request) {
//	public ApprovalStatusDto approvalVerification(@PathVariable int custid) {
			//int custid = customerService.findLastAddedCustomer();
//			int customerId = customer.getCustomerId();
			Customer c = customerService.findCustomerById(custid);
			ApprovalStatus s = customerService.customerVerification(custid);
			
			ApprovalStatusDto approvalStatusDto = new ApprovalStatusDto();
			approvalStatusDto.setApprovalStatus(c.getStatus());
			//approvalStatusDto.setMessage("APPROVED");
			if(s==ApprovalStatus.APPROVED) {
				Account a = new Account();
				a.setCustomer(c);
				a.setAccountType(AccountType.SAVINGS);
				a.setBalance(1000);
				accountService.addAccountWithAnExistingCustomer(a, c);
				return approvalStatusDto;
			}
			else {
				String text = "Your account is rejected";
				String subject = "Account Rejection";
				emailService.sendEmailForNewRegistration(c.getEmail(), text, subject);
				c.setStatus(ApprovalStatus.REJECTED);
				customerService.addOrUpdateACustomer(c);
				return approvalStatusDto;
			}
			
			
	}
	
	
	@PostMapping("/login")
	//http://localhost:8585/login
	public LoginStatus login(@RequestBody LoginDto loginDto) {
		try {
			Customer customer = customerService.login(loginDto.getEmail(), loginDto.getPassword());
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.SUCCESS);
			loginStatus.setMessage("Login Successful!");
			loginStatus.setCustomerId(customer.getCustomerId());
			loginStatus.setName(customer.getFirstName());
			return loginStatus;
		}
		catch(Exception e) {
			LoginStatus loginStatus = new LoginStatus();
			loginStatus.setStatus(StatusType.FAILURE);
			loginStatus.setMessage(e.getMessage());
			return loginStatus;
		}
	}

	
	@PostMapping("/registerNetbanking")
	//http://localhost:8585/registerNetbanking
	public Status registerNetbanking(@RequestBody RegisterDto registerDto) {
			int accNo = registerDto.getAccountNo();
			Customer c = accountService.findCustomerByAccountNo(accNo);
			Account acc = accountService.findAccountByAccountNo(accNo);
			
			if(acc != null) {
				long count = transactionService.checkAccountForNetBanking(accNo);
				if(count == 0) {
					Register r = new Register();
					r.setAccount(acc);
					r.setTransactionPassword(registerDto.getTransactionPassword());
					transactionService.addNetBankingAccount(r,c);
					Status status = new Status();
		            status.setStatus(StatusType.SUCCESS);
		            status.setMessage("Registered for Net Banking");
		            return status;
				}
				else {
					 Status status = new Status();
		             status.setStatus(StatusType.FAILURE);
		             status.setMessage("Net Banking Already registered");
		             return status;
					}
			}
			else {
			 Status status = new Status();
             status.setStatus(StatusType.FAILURE);
             status.setMessage("Account does not exist");
             return status;
			}
	}
	

	@GetMapping("/getCustomerDetails")
	//http://localhost:8585/getCustomerDetails
	public List<Customer> getCustomerDetails() {
		List<Customer> customers = customerService.viewAllCustomerAdmin();
		return customers;
	}
	
	@PostMapping("/addBeneficiary")
	//http://localhost:8585/addBeneficiary
	public Status addBeneficiary(@RequestBody BeneficiaryDto beneficiaryDto) {
		Customer c = customerService.findCustomerById(beneficiaryDto.getCustomerId());
		Account fromAcc = accountService.findAccountByAccountNo(beneficiaryDto.getFromAccountNo());
		
		try {
			Register register = transactionService.getRegisterByAccountNo(beneficiaryDto.getFromAccountNo());
			
				if(register != null) {
					long count = transactionService.checkBeneficiaryforAccount(beneficiaryDto.getFromAccountNo(), beneficiaryDto.getToAccountNo());
					if(count == 0) {
						Beneficiary b = new Beneficiary();
						b.setBeneficiaryName(beneficiaryDto.getBeneficiaryName());
						b.setAccount(fromAcc);
						b.setToAccountNo(beneficiaryDto.getToAccountNo());
						b.setCustomer(c);
						transactionService.addBeneficiaryToAnAccount(b);
						//System.out.println("Beneficiary added");
						Status status = new Status();
					    status.setStatus(StatusType.SUCCESS);
					    status.setMessage("Beneficiary added");
					    return status;
					}
					else {
						//System.out.println("Already Present");
						Status status = new Status();
					    status.setStatus(StatusType.FAILURE);
					    status.setMessage("Beneficiary already exists");
					    return status;
					}
				}
				else {
					Status status = new Status();
				    status.setStatus(StatusType.FAILURE);
				    status.setMessage("Not registered for net banking");
				    return status;
				}
				
		} catch (Exception e) {
			Status status = new Status();
		    status.setStatus(StatusType.FAILURE);
		    status.setMessage("Not registered for net banking");
		    return status;
		}
	}
	
	@GetMapping("/getAllBeneficiaries")
	//http://localhost:8585/getAllBeneficiaries
	public List<Beneficiary> getAllBeneficiaries(@RequestParam("custId") int customerId, HttpServletRequest request) {
		List<Beneficiary> beneficiaries = transactionService.fetchAllBeneficiaryOfCustomer(customerId);
		return beneficiaries;
	}
	
	 @GetMapping("/getCustomerDetailsById")
	    //http://localhost:8585/getCustomerDetailsById
	    public CustomerWithAddressDto getCustomerDetailsById(@RequestParam("custId") int custid, HttpServletRequest request) {
	        Customer c = customerService.findCustomerById(custid);
	        Address addr = customerService.getCustomerAddressByCustomerId(custid);
	        CustomerWithAddressDto customerWithAddressDto = new CustomerWithAddressDto();
	        customerWithAddressDto.setFirstName(c.getFirstName());
	        customerWithAddressDto.setMobile(c.getMobile());
	        customerWithAddressDto.setEmail(c.getEmail());
	        customerWithAddressDto.setAadharNo(c.getAadharNo());
	        customerWithAddressDto.setDateOfBirth(c.getDateOfBirth());
	        customerWithAddressDto.setPanCard(c.getPanCard());
	        customerWithAddressDto.setOccupationType(c.getOccupationType());
	        customerWithAddressDto.setAnnualIncome(c.getAnnualIncome());
	        customerWithAddressDto.setAddress(addr.getAddress());
	        customerWithAddressDto.setLandmark(addr.getLandmark());
	        customerWithAddressDto.setCity(addr.getCity());
	        customerWithAddressDto.setState(addr.getState());
	        customerWithAddressDto.setPincode(addr.getPincode());
	        return customerWithAddressDto;
	    }
	

	    @GetMapping("/getAccountDetailsByCustId")
	    //http://localhost:8585/getAccountDetailsByCustId
	    public List<Account> getAccountDetailsByCustId(@RequestParam("custId") int custid, HttpServletRequest request) {
	        List<Account> a=accountService.getAllAccountsOfACustomer(custid);
	        return a;
	    }
	    
	    @PostMapping("/addATransactionToAnAccount")
		//http://localhost:8585/addATransactionToAnAccount
		public Status addATransactionToAnAccount(@RequestBody TransactionDto tdto) {
	    	
	    	String transactionPassword = tdto.getTpassword();
	    	int accNo = tdto.getFromAccountNo();
	    	
	    	Register register = transactionService.getRegisterByAccountNo(accNo);
	    	
	    	if( register.getTransactionPassword().equalsIgnoreCase(transactionPassword)) {
	    		Beneficiary b = transactionService.findBeneficiaryById(tdto.getBeneficiaryId());
				Account fromAcc = accountService.findAccountByAccountNo(tdto.getFromAccountNo());
				
				Transaction t1 = new Transaction();
				t1.setTransactionDate(LocalDate.parse(tdto.getTransactionDate()));
				t1.setAmount(tdto.getAmount());
				t1.setTransferType(tdto.getTransactionType());
				t1.setDescription(tdto.getDescription());
				t1.setAccount(fromAcc);
				t1.setToAccountNumber(tdto.getToAccountNo());
				
				List<Transaction> trans = new ArrayList<Transaction>();
				trans.add(t1);
				fromAcc.setTransaction(trans);
				fromAcc.setBalance(fromAcc.getBalance()-t1.getAmount());
				
				Account toAccount=accountService.findAccountByAccountNo(tdto.getToAccountNo());
				toAccount.setBalance(toAccount.getBalance()+t1.getAmount());
				accountService.addAccountWithAnExistingCustomer(toAccount);
			     
				Transaction t = transactionService.addATransactionToAnAccount(t1);
				System.out.println(fromAcc.getAccountNo()+fromAcc.getBalance());
				Status s = new Status();
				s.setStatus(StatusType.SUCCESS);
				return s;
	    	}
	    	else {
	    		Status s = new Status();
				s.setStatus(StatusType.FAILURE);
				s.setMessage("Net Banking registration not done");
				return s;
	    	}
			
		}
	    
	    
	    @PostMapping("/forgetPassword")
	    //http://localhost:8585/forgetPassword
	    public Status forgetPassword(@RequestBody ForgetPassword forgetPassword){
	    	int customerId = forgetPassword.getCustomerId();
	    	try {
	    		Customer c = customerService.findCustomerById(customerId);
	    		if(c != null) {
	    			customerService.forgetPassword(customerId);
					Status s = new Status();
					s.setStatus(StatusType.SUCCESS);
					s.setMessage("Password updated");
					return s;
	    		}
	    		else {
	    			Status s = new Status();
					s.setStatus(StatusType.FAILURE);
					s.setMessage("Customer not found");
					return s;
	    		}
			} catch (Exception e) {
				Status s = new Status();
				s.setStatus(StatusType.FAILURE);
				return s;
			}
	    }
	    
	    
	    @GetMapping("/getAllTransactionByCustomerId")
		//http://localhost:8585/getAllTransactionByCustomerId
		public List<Transaction> getAllTransactionByCustomerId(@RequestParam("custId") int customerId, HttpServletRequest request) {
	    	Customer c = customerService.findCustomerById(customerId);
	    	int accNo = accountService.findAccountNoByCustomerId(customerId);
	    	List<Transaction> transactions = transactionService.fetchAllTransactionsOfAnAccount(accNo);
			return transactions;
		}
	    
	    @PostMapping("/getAllTransactionByDate")
		//http://localhost:8585/getAllTransactionByDate
		public List<Transaction> getAllTransactionByDate(@RequestBody StatementDto statementDto, HttpServletRequest request) {
	    	List<Transaction> transactions = transactionService.fetchTransactionByDate(statementDto);
			return transactions;
		}
	    
	    
	    @PostMapping("/aadhar-upload")
	    //http://localhost:8585/aadhar-upload
		public Status uploadAadhar(AadharDto aadharDto) {
	    	int custid = customerService.findLastAddedCustomer();
			String imageUploadLocation = "d:/uploads/";
			String fileName = aadharDto.getAadharCard().getOriginalFilename();
			String targetFile = imageUploadLocation + fileName;
			try {
				FileCopyUtils.copy(aadharDto.getAadharCard().getInputStream(), new FileOutputStream(targetFile));
			} catch (IOException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatus(StatusType.FAILURE);
				status.setMessage(e.getMessage());
				return status;
			}
			Customer customer = customerService.findCustomerById(custid);
			customer.setAadharCardFile(fileName);
			customerService.addOrUpdateACustomer(customer);
			
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Aadhar Uploaded!");
			return status;
		}
		
		@PostMapping("/pan-upload")
		public Status uploadPan(panCardDto panCardDto) {
			int custid = customerService.findLastAddedCustomer();
			String imageUploadLocation = "d:/uploads/";
			String fileName = panCardDto.getPanCard().getOriginalFilename();
			String targetFile = imageUploadLocation + fileName;
			try {
				FileCopyUtils.copy(panCardDto.getPanCard().getInputStream(), new FileOutputStream(targetFile));
			} catch (IOException e) {
				e.printStackTrace();
				Status status = new Status();
				status.setStatus(StatusType.FAILURE);
				status.setMessage(e.getMessage());
				return status;
			}
			Customer customer = customerService.findCustomerById(custid);
			customer.setPanCardFile(fileName);
			customerService.addOrUpdateACustomer(customer);
			
			Status status = new Status();
			status.setStatus(StatusType.SUCCESS);
			status.setMessage("Pan Uploaded!");
			return status;
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
//	@GetMapping("/getAllBeneficiaries/{customerId}")
//	//http://localhost:8585/getAllBeneficiaries/109
//	public List<Beneficiary> getAllBeneficiaries(@PathVariable int customerId) {
//		List<Beneficiary> beneficiaries = transactionService.fetchAllBeneficiaryOfCustomer(customerId);
//		return beneficiaries;
//	}

	
	
	
//	@PostMapping("/addAddressToACustomer")
//	//http://localhost:8585/addAddressToACustomer
//	public void approvalVerification(@RequestBody Address address) {
//			int custid = customerService.findLastAddedCustomer();
//			Customer c = customerService.findCustomerById(custid);
//			address.setCustomer(c);
//			customerService.addAddressToACustomer(address);
//			Verification v = new Verification();
//			v.setCustomer(c);
//			
//		
//	}
	
	
	
	
	
//  @PostMapping(value="/login")
//  //http://localhost:8585/login
//  public String loginUser(@RequestBody LoginDto loginDto) {
//      boolean success = customerService.login(loginDto.getEmail(), loginDto.getPassword());
//      return success?"Valid User":"Login failed";
//  }
	
//	@PostMapping("/addAddressToACustomer/{{customerId}}")
//	//http://localhost:8585/addAddressToACustomer/55
//	//http://localhost:8585/BankREst/rest/bank/addAddressToACustomer/7
//	public void addAddressToACustomer(@RequestBody Address address, @RequestParam("customerId") int customerId) {
//		Customer c = customerService.findCustomerById(customerId);
//		if(c.getAddress() == null) {
//			address.setCustomer(c);
////			c.setAddress(address);
//			customerService.addAddressToACustomer(address);
//		}
//		else {
//			System.out.println("Address exists for customer");
//		}	
//	}
	
	

	
//  @PostMapping("/login")
//	public LoginStatus login(@RequestBody LoginDto loginDto) {
//		try {
//			Customer customer = customerService.login(loginDto.getEmail(), loginDto.getPassword());
//			LoginStatus loginStatus = new LoginStatus();
//			loginStatus.setStatus(StatusType.SUCCESS);
//			loginStatus.setMessage("Login Successful!");
//			loginStatus.setCustomerId(customer.getCustomerId());
//			loginStatus.setName(customer.getFirstName());
//			return loginStatus;
//		}
//		catch(Exception e) {
//			LoginStatus loginStatus = new LoginStatus();
//			loginStatus.setStatus(StatusType.FAILURE);
//			loginStatus.setMessage(e.getMessage());
//			return loginStatus;
//		}
//	}
	
	
	
	
	
//	@PostMapping("/addOrUpdateACustomer")
//	//http://localhost:8585/addOrUpdateACustomer
//	public Status addorUpdateCustomer(@RequestBody Customer customer) {
//		 try {
//             customerService.addOrUpdateACustomer(customer);
//            
//             Status status = new Status();
//             status.setStatus(StatusType.SUCCESS);
//             status.setMessage("Registration successful!");
//             return status;
//         }
//         catch(Exception e) {
//             Status status = new Status();
//             status.setStatus(StatusType.FAILURE);
//             status.setMessage(e.getMessage());
//             return status;
//         }
//	}
	
	
//	@PostMapping("/addAddressToACustomer/{customerId}")
//	//http://localhost:8585/addAddressToACustomer/55
//	//http://localhost:8585/BankREst/rest/bank/addAddressToACustomer/7
//	public void addAddressToACustomer(@PathVariable int customerId, @RequestBody Address address) {
//		Customer c = customerService.findCustomerById(customerId);
//		if(c.getAddress() == null) {
//			Address add = new Address();
//			//add.setAddressId(104);//will update the address
//			add.setAddress(address.getAddress());
//			add.setLandmark(address.getLandmark());
//			add.setCity(address.getCity());
//			add.setState(address.getState());
//			add.setPincode(address.getPincode());
//			add.setCustomer(c);
//			customerService.addAddressToACustomer(add);
//		}
//		else {
//			System.out.println("Address exists for customer");
//		}	
//	}
//	

	//@GetMapping(value= "/customerAddress/{customerId}")
	@RequestMapping(value = "/customerAddress/{customerId}", method= RequestMethod.GET)
	//http://localhost:8585/customerAddress
	public Address getCustomerAddressByCustomerId(@PathVariable int customerId) {
			return customerService.getCustomerAddressByCustomerId(customerId);
	}
	
//	@PostMapping("/addAccountToACustomer/{customerId}")
//	//http://localhost:8585/addAccountToACustomer/55
//	//http://localhost:8585/BankREst/rest/bank/addAccountToACustomer/55
//	public void addAccountToACustomer(@PathVariable int customerId, @RequestBody Account account) {  
//		Customer c = customerService.findCustomerById(customerId);
//		//check if address is present 
//		//findAddressByCustomerId
//		Account a= new Account();
//		a.setStatus(account.getStatus());
//		a.setBalance(account.getBalance());
//		a.setAccountType(account.getAccountType());
//		a.setCustomer(c);
//		accountService.addAccountWithAnExistingCustomer(a, c);
//	}
	
	
}
