package org.nix.common.resolver;
import org.nix.annotation.CurrentUser;
import org.nix.entity.SysUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
/**
 * Create by zhangpe0312@qq.com on 2018/4/4.
 *
 * @description :自定义解析器实现参数绑定 增加方法注入，
 * 将含有 @CurrentUser 注解的方法参数注入当前登录用户
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver{
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(SysUser.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SysUser user = (SysUser) webRequest
                .getAttribute(CurrentUserConstants.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestPartException(CurrentUserConstants.CURRENT_USER);
    }
}
