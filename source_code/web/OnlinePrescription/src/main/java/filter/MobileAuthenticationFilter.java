package filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MobileAuthenticationFilter extends AbstractMobileAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        log.info("安卓用户进入校验！" + getLoginUrl());

        HttpServletRequest req = (HttpServletRequest) request;

        String token = req.getParameter(TOKEN);
        if (isAccess(token)) {
            return onAccessSuccess(req, (HttpServletResponse) response);
        }
        return onAccessFail(req, (HttpServletResponse) response);
    }

    @Override
    public boolean isAccess(String token) {
        log.info("安卓用户进入校验！isAccess");
        return false;
    }

    @Override
    public boolean onAccessSuccess(HttpServletRequest request, HttpServletResponse response) {
       log.info("安卓用户进入校验！onAccessSuccess");
        return false;
    }

    @Override
    public boolean onAccessFail(HttpServletRequest request, HttpServletResponse response) {
        log.info("安卓用户进入校验！onAccessFail");
        return false;
    }
}


