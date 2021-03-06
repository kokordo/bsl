package org.boilit.bsl;

import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.formatter.IFormatter;
import org.boilit.bsl.xio.FileResourceLoader;
import org.boilit.bsl.xio.IResourceLoader;
import org.boilit.bsl.xtp.DefaultTextProcessor;
import org.boilit.bsl.xtp.ITextProcessor;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public final class Engine {
    public static final String PROPERTIES_BSL = "bsl.properties";
    private String inputEncoding;
    private String outputEncoding;
    private boolean specifiedEncoder;
    private boolean useTemplateCache;
    private IResourceLoader resourceLoader;
    private ConcurrentMap<String, Template> templateCache;
    private ITextProcessor textProcessor;
    private final FormatterManager formatterManager;

    public Engine() {
        this.inputEncoding = null;
        this.outputEncoding = "UTF-8";
        this.specifiedEncoder = false;
        this.useTemplateCache = true;
        this.textProcessor = new DefaultTextProcessor();
        this.formatterManager = new FormatterManager();
        this.templateCache = new ConcurrentHashMap<String, Template>();
    }

    public static final Engine getEngine() throws Exception {
        return getEngine(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_BSL));
    }

    public static final Engine getEngine(InputStream inputStream) throws Exception {
        Properties properties = new Properties();
        properties.load(inputStream);
        return getEngine(properties);
    }

    public static final Engine getEngine(Properties properties) throws Exception {
        if (properties == null) {
            return new Engine();
        }
        Engine engine = new Engine();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final String inputEncoding = properties.getProperty("inputEncoding");
        if (inputEncoding != null && inputEncoding.trim().length() > 0) {
            if (inputEncoding.trim().equalsIgnoreCase("system")) {
                engine.setInputEncoding(System.getProperty("file.encoding"));
            } else {
                engine.setInputEncoding(inputEncoding.trim());
            }
        }
        final String outputEncoding = properties.getProperty("outputEncoding");
        if (outputEncoding != null && outputEncoding.trim().length() > 0) {
            engine.setOutputEncoding(outputEncoding.trim());
        }
        final String specifiedEncoder = properties.getProperty("specifiedEncoder");
        if (specifiedEncoder != null && specifiedEncoder.trim().length() > 0) {
            engine.setSpecifiedEncoder(Boolean.parseBoolean(specifiedEncoder.trim()));
        }
        final String useTemplateCache = properties.getProperty("useTemplateCache");
        if (useTemplateCache != null && useTemplateCache.trim().length() > 0) {
            engine.setUseTemplateCache(Boolean.parseBoolean(useTemplateCache.trim()));
        }
        final String resourceLoader = properties.getProperty("resourceLoader");
        if (resourceLoader != null && resourceLoader.trim().length() > 0) {
            Class clazz = classLoader.loadClass(resourceLoader.trim());
            IResourceLoader loader = (IResourceLoader) clazz.newInstance();
            loader.setEncoding(engine.getInputEncoding());
            engine.setResourceLoader(loader);
        }
        final String textProcessor = properties.getProperty("textProcessor");
        if (textProcessor != null && textProcessor.trim().length() > 0) {
            engine.setTextProcessor((ITextProcessor) classLoader.loadClass(textProcessor.trim()).newInstance());
        }
        return engine;
    }

    public final String getInputEncoding() {
        return inputEncoding;
    }

    public final void setInputEncoding(final String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    public final String getOutputEncoding() {
        return outputEncoding;
    }

    public final void setOutputEncoding(final String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }

    public final boolean isSpecifiedEncoder() {
        return specifiedEncoder;
    }

    public final void setSpecifiedEncoder(final boolean specifiedEncoder) {
        this.specifiedEncoder = specifiedEncoder;
    }

    public final boolean isUseTemplateCache() {
        return useTemplateCache;
    }

    public final void setUseTemplateCache(final boolean useTemplateCache) {
        this.useTemplateCache = useTemplateCache;
    }

    public final IResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public final void setResourceLoader(final IResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public final ITextProcessor getTextProcessor() {
        return textProcessor;
    }

    public final void setTextProcessor(final ITextProcessor textProcessor) {
        this.textProcessor = textProcessor == null ? new DefaultTextProcessor() : textProcessor;
    }

    public final IFormatter registerFormatter(final Class clazz, final IFormatter formatter) {
        return formatterManager.add(clazz, formatter);
    }

    public final void clearTemplateCache() {
        templateCache.clear();
    }

    public final Template getTemplate(final String name) throws Exception {
        IResourceLoader resourceLoader = this.resourceLoader;
        if (resourceLoader == null) {
            resourceLoader = this.resourceLoader = new FileResourceLoader();
        }
        resourceLoader.setEncoding(this.getInputEncoding());
        if (!useTemplateCache) {
            return new Template(this, resourceLoader.getResource(name), formatterManager);
        }
        Template template = templateCache.get(name);
        if (template == null) {
            templateCache.put(name, template = new Template(this, resourceLoader.getResource(name), formatterManager));
        }
        return template;
    }
}
