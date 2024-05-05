package ice.spot.repository;

import ice.spot.domain.User;
import ice.spot.dto.type.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u.id as id, u.role as role, u.password as password from User u where u.serialId = :serialId")
    Optional<UserSecurityForm> findUserSecurityFromBySerialId(String serialId);
    @Query("select u.id as id, u.role as role, u.password as password from User u where u.id = :id")
    Optional<UserSecurityForm> findUserSecurityFromById(Long id);
    Optional<User> findByIdAndRefreshToken(Long id, String refreshToken);
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.refreshToken = :refreshToken where u.id = :userId")
    void updateRefreshToken(Long userId, String refreshToken);
    Optional<User> findById(Long userId);

    interface UserSecurityForm {

        Long getId();
        ERole getRole();
        String getPassword();

        static UserSecurityForm invoke(User user){
            return new UserSecurityForm() {
                @Override
                public Long getId() {
                    return user.getId();
                }

                @Override
                public ERole getRole() {
                    return user.getRole();
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }
            };
        }
    }
}
