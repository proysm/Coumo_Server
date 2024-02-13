package coumo.server.service.customer;

import coumo.server.converter.CustomerConverter;
import coumo.server.domain.Customer;
import coumo.server.domain.Owner;
import coumo.server.repository.CustomerRepository;
import coumo.server.web.dto.CustomerRequestDTO;
import coumo.server.web.dto.OwnerRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Customer findByLoginId(String loginId) {
        //loginId기반으로 계정조회
        return customerRepository.findByLoginId(loginId);
    }

    @Override
    public Customer joinCustomer(CustomerRequestDTO.CustomerJoinDTO request){

        Customer newCustomer = CustomerConverter.toCustomer(request);
        // 비밀번호를 BCrypt로 인코딩
        newCustomer.setPassword(passwordEncoder.encode(request.getPassword()));
        return customerRepository.save(newCustomer);
    }

    // 로그인 기능
    @Override
    public Customer loginCustomer(CustomerRequestDTO.CustomerLoginDTO request){
        String loginId = request.getLoginId();
        String password = request.getPassword();

        // loginId로 계정 조회.
        Customer customer = findByLoginId(loginId);

        // 소유자가 존재하고 비밀번호가 일치하는지 확인합니다.
        if (customer != null && isPasswordMatch(password, customer.getPassword())) {
            return customer; // 로그인 성공 시 소유자 엔터티를 반환합니다.
        } else {
            return null; // 로그인 실패 시 null을 반환하거나 해당하는 방식으로 처리합니다.
        }
    }

    private boolean isPasswordMatch(String rawPassword, String encodedPassword){
        // BCryptPasswordEncoder를 사용하여 비밀번호 일치 여부 확인
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
