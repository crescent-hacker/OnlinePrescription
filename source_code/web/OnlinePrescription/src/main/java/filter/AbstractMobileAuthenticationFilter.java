package filter;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractMobileAuthenticationFilter extends
        AuthenticationFilter {

    public static final String TOKEN = "token";
    protected Logger log = Logger.getLogger(getClass());

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        log.info("android user coming in！" + getLoginUrl());

        HttpServletRequest req = (HttpServletRequest) request;

        String token = req.getParameter(TOKEN);
        if (isAccess(token)) {
            return onAccessSuccess(req, (HttpServletResponse) response);
        }
        return onAccessFail(req, (HttpServletResponse) response);
    }

    /**
     * check if token is valid
     *
     * @param token
     * @return
     */
    public abstract boolean isAccess(String token);

    /**
     * operation after authentication succeeded
     *
     * @param request
     * @param response
     * @return true-  need process subsequently，false - no need to
     */
    public abstract boolean onAccessSuccess(HttpServletRequest request,
                                            HttpServletResponse response);

    /**
     * process authentication fail result
     *
     * @param request
     * @param response
     * @return true-  need process subsequently，false - no need to
     */
    public abstract boolean onAccessFail(HttpServletRequest request,
                                         HttpServletResponse response);

}


