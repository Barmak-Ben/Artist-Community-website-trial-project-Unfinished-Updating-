package com.c.artifactwebproject.service;
import com.c.artifactwebproject.dto.LoginRequest;
import com.c.artifactwebproject.dto.UserCreateRequest;
import com.c.artifactwebproject.dto.UserResponse;   //这三个爆红的类我还没写，暂时无法用
import com.c.artifactwebproject.exception.ErrorCode;
import com.c.artifactwebproject.exception.BusinessException ;
import com.c.artifactwebproject.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.c.artifactwebproject.entity.User;
import org.springframework.stereotype.Service;   //构造器注解，将@Service标记为service层并注册为bean交给spring容器管理，其他Controll可以通过@Autowired操作


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public  UserService(UserRepository userRepository ,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional//这个注解将整个代码块变成原子化事务，入参，查重，加密，入库，但凡抛出异常直接回滚，事务写操作
    public UserResponse createUser(UserCreateRequest request ) {
    String username = request.username().trim();//这一段是注册业务逻辑，request是枚举，其中静态数据通过.name（）直接访问,trim()消去首尾空格
    if (userRepository.existsByUsername(username)) {
        throw new BusinessException(ErrorCode.USERNAME_TAKEN);  //这就是一场报错的使用，非常简单，直接照抄参数写法就行，爆红检查import是不是没写进来
    }//一旦遇到异常直接事务回滚，抛出异常
    User user = new User();//对内创建数据库实体
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(request.password()));//哈希加密，密码不以明文出现
    user.setNickname(request.nickname());
    user.setRole(User.Role.USER);//写死权限防止role = admin权限泄露
    User saved =  userRepository.save(user);//生成INSERT带主键持久化对象saved，
    return UserResponse.from(saved);//用静态工厂将持久化对象转化出参DTO，剔除密码，安全返回
    }

    @Transactional(readOnly = true)  //login是只读事务操作，这个标记将会使spring容器传递给JDBC优化操作，节约性能
    public UserResponse login(LoginRequest request )
    {
        User user = userRepository.findByUsername(request.username().trim())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));//查不到咱直接抛错误

            if (!passwordEncoder.matches(request.password(), user.getPassword())) {//明文校验密码，明文在前，散列在后；matches 内部会从数据库散列里取出盐，用同样算法重算一遍再比对。因为 BCrypt 盐是内嵌的，所以不需要单独存 salt 字段。
                throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
            }

            if (!Boolean.TRUE.equals(user.getEnabled()))//检验用户是否被禁用
            {
                throw new BusinessException(ErrorCode.USER_DISABLED);
            }

            return UserResponse.from(user);



    }
}
