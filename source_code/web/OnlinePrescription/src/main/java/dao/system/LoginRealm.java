package dao.system;

import domainModel.system.ClientSource;
import domainModel.system.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import service.system.ILoginService;

import java.util.HashSet;
import java.util.Set;

public class LoginRealm extends AuthorizingRealm {

    @Autowired
    ILoginService loginService;

    /* 
     * Grant
     */
    @Override
    //TODO,permission add and role add
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roleNames = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        principals.getPrimaryPrincipal();
//        roleNames.add("administrator");//add role
//        permissions.add("newPage.jhtml");  //add right
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    /* 
     * login verification
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        //1.get info
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String clientUserName = token.getUsername();
        String clientHashPassword = new String(token.getPassword());

        User user = loginService.getUser(clientUserName, true);
        if(user==null){
            throw new UnknownAccountException("Username or password error");
        }

        //2.re-compute and verify password
        if (!clientHashPassword.equals(user.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }

        //3.login verify success and update login times
        loginService.updateLoginTimes(clientUserName, ClientSource.WEB);

        return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(),getName());
    }

}