package com.practice.mvc.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.practice.mvc.constants.WebConstants;
import com.practice.mvc.handler.BaseEventHandler;

public class OrganizaitonControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, BaseEventHandler>   events;
	
    public OrganizaitonControllerServlet() {
        super();
    }

    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        
        String resourceName = null;
        if (config != null) {
            resourceName = config.getInitParameter(WebConstants._RESOURCE);
        }else{
            resourceName = getInitParameter(WebConstants._RESOURCE);
        }
        if (resourceName == null || resourceName.trim().length() == 0) {
            throw new ServletException("No resource has been specified for the OrganizationControllerServlet.");
        }
        ResourceBundle bundle = ResourceBundle.getBundle(resourceName);
        if (bundle == null) {
            String error = "No resouce bundle under name of " + resourceName + " can be found.";
            throw new ServletException(error);
        }
        
        //Initialize HashMap
        this.events = new HashMap<String, BaseEventHandler>();
        // get event values and save them into events
        Enumeration<String> e = bundle.getKeys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            String tempKey = null;
            if(key != null){
            	tempKey = key.trim();
            }
            String value = bundle.getString(tempKey);
            String tempValue = null;
            if ( value != null){
            	tempValue = value.trim();
            }
            
            if (tempKey != null && tempKey.trim().length() > 0 &&
            		tempValue != null && tempValue.length() > 0) {
                try {
                    BaseEventHandler handler = (BaseEventHandler)Class.forName(tempValue).newInstance();
                    this.events.put(tempKey, handler);
                }catch(ClassNotFoundException cnfe) {
                    String error = "No class [" + tempValue + "] can be found.";
                    throw new ServletException(error);
                }catch(InstantiationException ie) {
                    String error = "The handler class [" + tempValue + "] cannot be instantiated.";
                    throw new ServletException(error);
                }catch(IllegalAccessException iae) {
                    String error = "The handler class [" + tempValue + "] is not accessible.";
                    throw new ServletException(error);
                }
            } 
        }
        
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            process(request, response);
    	} catch (Throwable e){
        	System.out.println("OrganizationController Caught an Exception "+e);
        	RequestDispatcher rd = request.getRequestDispatcher("/organization/error.jsp");
        	rd.forward(request, response);
    	}
	}
	
	private void process(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
		
        
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=utf-8");
        
        String requestAction = req.getParameter(WebConstants.ACTION);
        BaseEventHandler handler = null;
        if (requestAction != null && requestAction.trim().length() > 0) {
        	String resourceName = getInitParameter(WebConstants._RESOURCE);
        	System.out.println("Properties file name: "+resourceName+".properties");
        	ResourceBundle bundle = ResourceBundle.getBundle(resourceName);
            if (bundle == null) {
                 String error = "No resouce bundle under name of " + resourceName + " can be found.";
                 throw new ServletException(error);
            }
        	
        	String handlerName = bundle.getString(requestAction);
     		System.out.println("Value of key 'MainPage' is: "+handlerName);
        	
        	/*try {
				handler = (BaseEventHandler)Class.forName(handlerName.trim()).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}*/
     		
     		handler = this.events.get(requestAction);
     		
        }
        System.out.println("Handler name: "+handler);
        if (handler == null) {
            String msg = "Invalid action request: '" +  requestAction +
                    "' submitted by the user with URL [" + req.getRequestURI() + "].";
            throw new ServletException(msg);
        }
        
        String url = handler.process(req, resp);
        System.out.println("URL returned: "+url);
        if (url == null || url.trim().length() == 0) {
            String error = "The requested action handler [" + requestAction +
                    "] doesn't return targeted url.";
            throw new ServletException(error);
        }
        //String path = req.getContextPath();
        RequestDispatcher rd = req.getRequestDispatcher(url);
        if (rd == null) {
            String error = "No following page has been defined under action";
            throw new ServletException(error);
        }
        rd.forward(req, resp);
		
	}
}
