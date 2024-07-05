<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.ATMListCtl"%>
<%@page import="com.rays.pro4.Bean.ATMBean"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>ATM List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/ValidateToInput.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

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

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ATMBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.ATM_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>ATM List</h1>

				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				HashMap map=	(HashMap)request.getAttribute("map");
				List list = ServletUtility.getList(request);
				Iterator<ATMBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>

					<!-- 	<td align="center"> -->
					<label>Location:</font>
					</label>
					<%
						
							String hlist = HTMLUtility.getList("location", String.valueOf(bean.getLocation()), map);
					%>
					<%=hlist%>
					&nbsp; &nbsp;

					<label>Remark:</font>
					</label>
					<input type="text" name="remark" align="left"
						placeholder="Enter remark"
						onkeypress="return ValidateInput(event)"
						value="<%=ServletUtility.getParameter("remark", request)%>">
					&nbsp; &nbsp;


					

					&nbsp;
					<label> Date </font>
					</label>
					<input type="text" name="date" id="udatee" placeholder="Enter Last Restocked Date"
						value="<%=ServletUtility.getParameter("date", request)%>">
					&nbsp;


						
						<label>Cash Available:</font> 
					</label> <input type="text" name="cash"
						placeholder="Enter cash " 
						value="<%=ServletUtility.getParameter("cash", request)%>"> &nbsp;
						


				



					<input type="submit" name="operation"
						value="<%=ATMListCtl.OP_SEARCH%>"> &nbsp;
					<input type="submit" name="operation"
						value="<%=ATMListCtl.OP_RESET%>">
					</td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: pink">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Location</th>
					<th>Cash Available</th>
					<th>Last Restocked Date</th>
					<th>Remark</th>
					
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
				%>



				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getLocation()%></td>
					<td><%=bean.getCash()%></td>
					<td><%=bean.getDate()%></td>
					<td><%=bean.getRemark()%></td>
					
					
					<td><a href="ATMCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">


				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value=<%=ATMListCtl.OP_PREVIOUS%>></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value=<%=ATMListCtl.OP_PREVIOUS%>></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value=<%=ATMListCtl.OP_DELETE%>></td>
					<td><input type="submit" name="operation"
						value=<%=ATMListCtl.OP_NEW%>></td>


					<td align="right"><input type="submit" name="operation"
						value=<%=ATMListCtl.OP_NEXT%>
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>

				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value=<%=ATMListCtl.OP_BACK%>></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>

	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>

