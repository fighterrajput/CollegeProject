<%@page import="com.rays.pro4.controller.VehicleListCtl"%>
<%@page import="com.rays.pro4.Bean.VehicleBean"%>
<%@page import="java.util.HashMap"%>

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
<title>Vehicle List</title>
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
</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.VehicleBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.VEHICLE_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Vehicle List</h1>

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
				HashMap map = (HashMap) request.getAttribute("map");
				HashMap map1 = (HashMap) request.getAttribute("map1");

				List list = ServletUtility.getList(request);
				Iterator<VehicleBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>

					<label>Colour:</font>
					</label>
					<%
						String hlist1 = HTMLUtility.getList("colour", String.valueOf(bean.getColour()), map1);
					%>
					<%=hlist1%>
					&nbsp;

					<label>Purchase Date : </font>
					</label>
					<input type="text" name="purchasedate" id="udatee"
						placeholder="Enter Purchase Date"
						value="<%=ServletUtility.getParameter("purchasedate", request)%>">
					&nbsp;


					<label>Insurance Amount:</font>
					</label>
					<input type="text" name="insuranceamount"
						placeholder="Enter Insurance Amount"
						oninput="validateNumericInput(this)"
						value="<%=ServletUtility.getParameter("insuranceamount", request)%>">
					&nbsp;
				<tr>


					<label> Number : </font>
					</label>

					<input type="text" name="number" maxlength="16"
						placeholder="Enter Number" size="25"
						value="<%=ServletUtility.getParameter("number", request)%>">
					</td>

				</tr>
				&nbsp; &nbsp;

				<input type="submit" name="operation"
					value="<%=VehicleListCtl.OP_SEARCH%>"> &nbsp;
				<input type="submit" name="operation"
					value="<%=VehicleListCtl.OP_RESET%>">



			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: pink">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>

					<th>S.No.</th>
					<th>Number</th>
					<th>Purchase Date</th>
					<th>Insurance Amount</th>
					<th>Colour</th>


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

					<td><%=bean.getNumber()%></td>
					<td><%=bean.getPurchaseDate()%></td>
					<td><%=bean.getInsuranceAmount()%></td>
					<td><%=bean.getColour()%></td>


					<td><a href="VehicleCtl?id=<%=bean.getId()%>">Edit</a></td>
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
						value=<%=VehicleListCtl.OP_PREVIOUS%>></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_PREVIOUS%>></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_DELETE%>></td>
					<td><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_NEW%>></td>


					<td align="right"><input type="submit" name="operation"
						value=<%=VehicleListCtl.OP_NEXT%>
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>

				</tr>

			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value=<%=VehicleListCtl.OP_BACK%>></td>
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

