package filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthenticationFilter implements HandlerInterceptor {

    private static final Set<String> PUBLIC_PATHS = new HashSet<>();

    static {
        PUBLIC_PATHS.add("/");
        PUBLIC_PATHS.add("/login");
        PUBLIC_PATHS.add("/register");
        PUBLIC_PATHS.add("/error");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("user") != null;

        if (isPublicPath(path) || loggedIn || isResourcePath(path)) {
            return true;
        }

        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }

    private boolean isPublicPath(String path) {
        if (path == null) {
            return false;
        }
        if (PUBLIC_PATHS.contains(path)) {
            return true;
        }
        // Allow API endpoints through (token-based auth handled elsewhere)
        if (path.startsWith("/api/")) {
            return true;
        }
        return path.startsWith("/login") || path.startsWith("/register");
    }

    private boolean isResourcePath(String path) {
        return path != null && (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/webjars/") || path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg") || path.endsWith(".svg"));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // no-op
    }
}
