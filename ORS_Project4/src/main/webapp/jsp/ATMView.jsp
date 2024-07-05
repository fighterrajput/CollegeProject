<%@page import="com.rays.pro4.controller.ATMCtl"%>
<%@page import="com.rays.pro4.controller.ProductCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<script src="<%=ORSView.APP_CONTEXT%>/js/ValidateToInput.js"></script>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>ATM Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1900:2024',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>

<script>
	function validateNumericInput(inputField) {
		// Get the value entered by the user
		var inputValue = inputField.value;

		// Regular expression to check if the input is numeric
		var numericPattern = /^\d*$/;

		// Test the input value against the numeric pattern
		if (!numericPattern.test(inputValue)) {
			// If input is not numeric, clear the field
			inputField.value = inputValue.replace(/[^\d]/g, ''); // Remove non-numeric characters
		}
	}
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ATMBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.ATM_CTL%>" method="post">
			<%
				HashMap map = (HashMap) request.getAttribute("map");
			%>



			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update ATM </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add ATM</font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>

				<tr>
					<th align="left">Location <span style="color: red">*</span> :
					</th>
					<td>
						<%
							String hlist = HTMLUtility.getList("location", String.valueOf(bean.getLocation()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("location", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>


					<th align="left">Remark: <span style="color: red">*</span>

					</th>
					<td><input type="text" name="remark"
						placeholder="Enter remark"
						onkeypress="return ValidateInput(event)" size="25" 
						value="<%=DataUtility.getStringData(bean.getRemark())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("remark", request)%></font></td>

				</tr>





				<tr>
					<th style="padding: 3px"></th>
				</tr>




				<tr>
					<th align="left">Date<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="date" placeholder="Enter Date"
						size="25" readonly="readonly" id="udatee"
						value="<%=DataUtility.getDateString(bean.getDate())%>"></td>


					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("date", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Cash: <span style="color: red">*</span>

					</th>
					<td><input type="text" name="cash"
						placeholder="Enter Cash Available"
						 size="25" maxlength="21"
						value="<%=(DataUtility.getStringData(bean.getCash()).equals("0.0") ? ""
					: DataUtility.getStringData(bean.getCash()))%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("cash", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<%
					if (bean.getId() > 0) {
				%>

				&nbsp;
				<td align="right" colspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=ATMCtl.OP_UPDATE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=ATMCtl.OP_CANCEL%>"></td>

				<%
					} else {
				%>

				&nbsp;&nbsp;
				<td align="right" scolspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=ATMCtl.OP_SAVE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=ATMCtl.OP_RESET%>"></td>

				<%
					}
				%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>