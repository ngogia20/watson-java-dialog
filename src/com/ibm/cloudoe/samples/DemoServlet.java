/* Copyright IBM Corp. 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.cloudoe.samples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import com.ibm.cloudoe.utils.GetResponseData;



@MultipartConfig
public class DemoServlet extends HttpServlet {

	private static Logger logger = Logger
			.getLogger(DemoServlet.class.getName());
	private static final long serialVersionUID = 1L;
	private static final String CONVERSATION = "/conversation";
	private static final String PROFILE = "/profile";


	// If running locally complete the variables below
	// with the information in VCAP_SERVICES

	private String baseURL = "https://gateway.watsonplatform.net/dialog/api";
	private String username = "3f6f1a12-c6d8-4eed-8391-26813f9383bd";
	private String password = "g9IiXAlWtXeU";
	private String dialogId = "bf58fd1a-96a2-4ee9-934e-e2158d2dbb5c";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	/**
	 * Create and POST a request to the Dialog service
	 * 
	 * @param req
	 *            the Http Servlet request
	 * @param resp
	 *            the Http Servlet pesponse
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		//logger.info("doPost:" + req.getPathInfo());

		req.setCharacterEncoding("UTF-8");

		String path = req.getPathInfo();
		Request request = null;
		if(path.equals(PROFILE)) {
			return;
		}
		try {
			//if (path.equals(CONVERSATION))
				request = conversation(req);
			/*else if (path.equals(PROFILE))
				request = profile(req);*/
	
			Executor executor = Executor.newInstance().auth(username, password);
			Response response = executor.execute(request);

			HttpResponse httpResponse = response.returnResponse();
			resp.setStatus(httpResponse.getStatusLine().getStatusCode());
			
			
			
			ServletOutputStream servletOutputStream = resp.getOutputStream();
			httpResponse.getEntity().writeTo(servletOutputStream);

			
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			httpResponse.getEntity().writeTo(outstream);
			byte [] responseBody = outstream.toByteArray();
			String file_string="";
			for(int i = 0; i < responseBody.length; i++)
		    {
		        file_string += (char)responseBody[i];
		    }
			
			System.out.println("Nikesh_"+path+file_string);
			
			//String bstring = GetResponseData.parse(file_string);
			
			//System.out.println(bstring);
			
			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Service error: " + e.getMessage(), e);
			resp.setStatus(HttpStatus.SC_BAD_GATEWAY);
		}
	}

	/**
	 * Create a /profile request
	 *
	 * @param req the HTTP request
	 * @return the request
	 * @throws URISyntaxException 
	 */
	private Request profile(HttpServletRequest req) throws URISyntaxException {
		String clientId = req.getParameter("client_id");
		URI converseURI = new URI(baseURL + "/v1/dialogs/" + dialogId + "/profile?client_id="+ clientId).normalize();
		return Request.Get(converseURI);
	}

	private Request conversation(HttpServletRequest req) throws URISyntaxException {
		// create the request
		String input = req.getParameter("input");
		String conversationId = req.getParameter("conversation_id");
		String clientId = req.getParameter("client_id");
		System.out.println(conversationId);
		URI converseURI = new URI(baseURL + "/v1/dialogs/" + dialogId+ "/conversation").normalize();
		return Request.Post(converseURI).bodyForm(
			Form.form()
				.add("input", input)
				.add("client_id", clientId)
				.add("conversation_id", conversationId)
				.build());
	}

	@Override
	public void init() throws ServletException {
		super.init();
		dialogId = "bf58fd1a-96a2-4ee9-934e-e2158d2dbb5c";
		//processVCAPServices();
	}

	/**
	 * If exists, process the VCAP_SERVICES environment variable in order to get
	 * the username, password and baseURL
	 */
	private void processVCAPServices() {
		logger.info("Processing VCAP_SERVICES");
		//JSONObject sysEnv = getVCAPServices();

				baseURL = "https://gateway.watsonplatform.net/dialog/api";
				username = "197bc2b7-e5bb-4d1c-812e-fff39075c0f1";
				password = "PhPKrDDgF0NN";
	}

	/**
	 * Gets the <b>VCAP_SERVICES</b> environment variable and return it as a
	 * JSONObject.
	 * 
	 * @return the VCAP_SERVICES as Json
	 */
	
}
