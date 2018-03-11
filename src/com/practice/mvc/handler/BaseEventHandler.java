package com.practice.mvc.handler;

/**
 * This class encapsulates basic actions for handling event generated from web request. 
 *
 * @version 4.0
 * @since   Reqwired 4.0
 */
public abstract class BaseEventHandler {
    /**
     * Default constructor
     */
    protected BaseEventHandler() {
    }

    /**
     * This method handles business logic for this event.  It can be called by
     * controller to obtain the URL, which typically is a JSP, to forward.
     * This method should be overriden.
     * 
     * @param request   the request sent by client to the server
     * @param response  the response sent by server back to the client
     * @return  The string of the URL to forward.
     * @throws IOException - if I/O related error occurs
     * @throws ServletException - if servlet or other error occurs
     */
    public abstract String process(
            javax.servlet.http.HttpServletRequest   request,
            javax.servlet.http.HttpServletResponse  response)
        throws java.io.IOException, javax.servlet.ServletException;
}
