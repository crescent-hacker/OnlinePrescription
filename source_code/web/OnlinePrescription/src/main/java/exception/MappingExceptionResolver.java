package exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domainModel.system.JsonConstant;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MappingExceptionResolver implements
		HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object object, Exception exception) {
		if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader(
				"X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("success", false);
			if (exception instanceof BusinessException) {
				map.put("errorMsg", exception.getMessage());
			} else {
				map.put("errorMsg", "System error.");
			}
			exception.printStackTrace();
			return new ModelAndView("/error/error", map);
		} else {
			try {
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter writer = response.getWriter();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(JsonConstant.SUCCESS, false);
				if (exception instanceof BusinessException) {
					map.put(JsonConstant.ERR_MSG, exception.getMessage());
				} else {
					map.put(JsonConstant.ERR_MSG, "System error.");
				}
				String json = new ObjectMapper().writeValueAsString(map);
				writer.write(json);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
