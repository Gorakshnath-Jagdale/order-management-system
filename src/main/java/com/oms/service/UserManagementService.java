package com.oms.service;

import com.oms.mapper.UserDetailsMapper;
import com.oms.models.UserDetailsEntity;
import com.oms.models.repository.UserDetailsRepository;
import com.oms.models.repository.UserRoleManagerRepository;
import com.oms.models.repository.UserRoleRepository;
import com.oms.pojo.UserDetailsPojo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsMapper mapper;
    private final UserRoleRepository userRoleRepository;
    private final UserRoleManagerRepository userRoleManagerRepository;

    public String addNewUserDetails(UserDetailsPojo user) {

        var newUser = mapper.getUserDetailsEntity(user);
        var savedUser = userDetailsRepository.save(newUser);
        return savedUser.getUserFirstName() + " " + savedUser.getUserLastName() + " Save with Login Id -" + savedUser.getLoginId() + " User Id generated is :" + savedUser.getId();
    }

    public String deleteUserDetails(Long userId) {
        if (userDetailsRepository.existsById(userId)) {
            //Delete user operation
            userDetailsRepository.deleteById(userId);
            return userId + " User deleted ";

        } else {
            return "User Not Exist";
        }

    }

    public String updateUserDetails(UserDetailsPojo user) {
        if (userDetailsRepository.existsById(user.getId())) {
            var updatingUser = new UserDetailsEntity();
            mapper.updateUserDetailsEntity(updatingUser, user);
            var savedUser = userDetailsRepository.save(updatingUser);
            return savedUser.getUserFirstName() + " " + savedUser.getUserLastName() + " Save with Login Id -" + savedUser.getLoginId() + " User Id generated is :" + savedUser.getId();
        } else {
            return "Does Not Exist";
        }

    }

    public List<UserDetailsPojo> getAllUsers() {
        return userDetailsRepository.findAll().stream().map(mapper::getUserDetailsPojo).collect(Collectors.toList());
    }

    public UserDetailsPojo getUser(Long userId) throws Exception {
        var user = userDetailsRepository.findById(userId).orElseThrow(Exception::new);
        return mapper.getUserDetailsPojo(user);
    }

    public UserDetailsPojo login(UserDetailsPojo user) {
        var userDetails = userDetailsRepository.findByLoginIdIgnoreCaseAndUserPass(user.getLoginId(), user.getUserPass());
        if (userDetails != null) {
            return mapper.getUserDetailsPojo(userDetails);
        } else {
            return null;
        }
    }

    /**
     * TOP TO BOTTOM
     * This method return's list of  userIds for which given parameter is supervisor/manager
     * in case of manager he will get ->supervisors + staff under supervisors + self
     *
     * @param userID
     * @return
     */
    public Set<Integer> getTeamMemberList(long userID) {
        //check if this userId belongs to manager or supervisor
        //IF Manager then get list of supervisors under him and then get list of staff under all supervisors
        Set<Integer> staffMembers = new HashSet<>();

        if (validateUser(userID)) {
                            var role = userRoleManagerRepository.findById(userID);
            if (role.isPresent()) {
                var roleDetail = role.get();
                staffMembers = userDetailsRepository.findBySupervisorIdInAndActiveUser(Collections.singleton((int)userID));
                if (roleDetail.getUserRoleEntity().getRoleName().equalsIgnoreCase("MANAGER")) {
                    {
                        staffMembers.addAll(userDetailsRepository.findBySupervisorIdInAndActiveUser(staffMembers));
                    }
                }
            }
        }
        else
        {
            //throw invalid user -or not active
        }
        staffMembers.add((int)userID);
        return staffMembers;
    }

    public Set<Integer> getMangerAndSupervisor(long userId) throws Exception {
        if (validateUser(userId)) {
            var user = userDetailsRepository.findById(userId);
            if (user.isPresent()) {
                var superVisor = user.get().getSupervisorId();
                var manager = userDetailsRepository.getById((long)superVisor).getSupervisorId();
                return new HashSet<>(Arrays.asList(superVisor, manager));
            } else {
                throw new Exception("Invalid user ID");
            }
        } else {
            throw new Exception("Invalid user login Please check is user active!!");
        }
    }

    public boolean validateUser(long userId)
    {
        if (userRoleManagerRepository.existsByUserDetailsEntity_IdAndUserDetailsEntity_ActiveUserAndUserRoleEntity_ActiveRoleAndEndDateGreaterThanEqualAndBeginDateLessThanEqualAllIgnoreCase(userId, TRUE, TRUE, new Date(), new Date())) {
            return TRUE;
        }else
            return FALSE;
    }
}
