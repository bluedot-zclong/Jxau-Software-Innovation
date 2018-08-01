package cn.edu.jxau.web.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

/**
 * BasePathFreeMarkerView
 *
 */
public class BasePathFreeMarkerView extends FreeMarkerView {
    private String basePathKey = "base";// ${base}

    @Override
    protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
        String contextPath = request.getContextPath();
        request.setAttribute(basePathKey, contextPath);
    }

    @Override
    public boolean checkResource(Locale locale) throws Exception {
        boolean exists = super.checkResource(locale);
        logger.info("url:[" + getUrl() + "] exists:" + exists);
        return exists;
    }

    public String getBasePathKey() {
        return basePathKey;
    }

    public void setBasePathKey(String basePathKey) {
        logger.info("basePathKey:" + basePathKey);
        if (StringUtils.isBlank(basePathKey)) {
            throw new NullPointerException("basePathKey不能为空");
        }
        this.basePathKey = basePathKey;
    }

}
