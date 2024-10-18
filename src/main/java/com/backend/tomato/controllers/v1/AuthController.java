package com.backend.tomato.controllers.v1;
import java.io.*;
import com.backend.tomato.dao.OTPDao;
import com.backend.tomato.entitites.OneTimePassword;
import com.backend.tomato.entitites.User;
import com.backend.tomato.models.JwtRequest;
import com.backend.tomato.models.JwtResponse;
import com.backend.tomato.security.JwtHelper;
import com.backend.tomato.services.CustomUserDetailsService;
import com.backend.tomato.services.OTPEmailService;
import com.backend.tomato.services.UserService;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private OTPEmailService otpEmailService;

    @Autowired
    private OTPDao otpDao;

    @Value("${email.validation.api.url}")
    private String apiUrl;

    @Value("${email.validation.api.key}")
    private String apiKey;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse
                                        .builder()
                                        .jwtToken(token)
                                        .username(userDetails.getUsername())
                                        .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        logger.info(email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/currentuser")
    public Principal getLoggedInUser(Principal principal) {
        System.out.println("Inside Principal Return");
        System.out.println(principal);
        return principal;
    }

    @GetMapping("/currentusername")
    public String getCurrentUserName(Principal principal) {
        String email = principal.getName();
        return this.customUserDetailsService.getCurrentUserName(email);
    }

    @GetMapping("/currentuserrole")
    public String getCurrentUserRole(Principal principal) {
        String email = principal.getName();
        return this.customUserDetailsService.getCurrentUserRole(email);
    }
    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }
    @PostMapping("/createuser")
    public ResponseEntity<String> attemptUserCreation(@RequestBody User user) {
        System.out.println("In Create User");
        System.out.println(user.toString());
        String email=user.getEmail();

        if(this.userService.doesUserExistByEmail(email)){
            return new ResponseEntity<>("User Already Exists",HttpStatus.IM_USED);
        }

//        Email Exists Or Not In SMTP Server
//        OkHttpClient client = new OkHttpClient();
        OkHttpClient client = getClient();

        //        String apiUrl="https://api.apilayer.com/email_verification/check?email=";

        String[] parts = email.split("@");
        System.out.println(parts);
        String requestUrl;
        if(parts.length == 2){
            requestUrl=apiUrl + parts[0] + "%40" + parts[1];
            System.out.println(requestUrl);
        }else{
            System.out.println("Format Wrong");
            return new ResponseEntity<>("Invalid Email",HttpStatus.OK);
        }

        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("apikey", apiKey)  // Check the correct header name
                .method("GET", null)
                .build();
//
        try (Response response = client.newCall(request).execute()) {
            String jsonResponse=response.body().string();
            System.out.println(jsonResponse);
            Gson gson=new Gson();
            EmailVerificationResponse verificationResponse = gson.fromJson(jsonResponse, EmailVerificationResponse.class);
            if (!verificationResponse.isMxFound()) {
                System.out.println("Request Failed With Code: " + response.code());
                return new ResponseEntity<>("Invalid Email",HttpStatus.OK);
            }
        }
        catch (ExpiredJwtException e) {
            System.out.println("*************EXPIRED****************");
            String otpId = this.otpEmailService.sendEmail(email);
            return new ResponseEntity<>(otpId, HttpStatus.OK);
        }
        catch (IOException e){
            System.out.println("*************OTHER ONE****************");
            e.printStackTrace();
            return new ResponseEntity<>("Invalid Email",HttpStatus.OK);
        }
        String otpId = this.otpEmailService.sendEmail(email);
        return new ResponseEntity<>(otpId, HttpStatus.OK);
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<String> verifyOtp(@RequestBody User user,
                                            @RequestParam String id,
                                            @RequestParam Integer oneTimePasswordCode,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date expires) {
        OneTimePassword userOneTimePassword=new OneTimePassword();
        userOneTimePassword.setId(id);
        userOneTimePassword.setOneTimePasswordCode(oneTimePasswordCode);
        userOneTimePassword.setExpires(expires);

        String otpId = userOneTimePassword.getId();
        Optional<OneTimePassword> otpFromDB = otpDao.findById(otpId);

        if (otpFromDB.isPresent()) {
            OneTimePassword fetchedOTP = otpFromDB.get();
            System.out.println(fetchedOTP.getOneTimePasswordCode());
            System.out.println(fetchedOTP.getExpires());

            System.out.println(userOneTimePassword.getOneTimePasswordCode());

            Instant apiExpiration = userOneTimePassword.getExpires().toInstant();
            Instant dbExpiration = fetchedOTP.getExpires().toInstant();

            if (apiExpiration.isBefore(dbExpiration)) {
                if (userOneTimePassword.getOneTimePasswordCode().equals(fetchedOTP.getOneTimePasswordCode())) {
//                     Register User With This Particular OTP id
//                    Get User Mapped With This Particular OTP Id
                    this.userService.createUser(user);
                    return new ResponseEntity<>("OTP Verified", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Wrong OTP", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("OTP Expired", HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }
}
class EmailVerificationResponse {
    private boolean mx_found;

    public boolean isMxFound() {
        return mx_found;
    }
}