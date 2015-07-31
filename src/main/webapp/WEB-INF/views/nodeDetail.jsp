<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<!-- Basic Page Needs
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<meta charset="utf-8">
<title>Your page title here :)</title>
<meta name="description" content="">
<meta name="author" content="">

<!-- Mobile Specific Metas
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- FONT
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link href="http://fonts.googleapis.com/css?family=Raleway:400,300,600"
	rel="stylesheet" type="text/css">

<!-- CSS
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="stylesheet" href="<c:url value="/css/normalize.css"/>">
<link rel="stylesheet" href="<c:url value="/css/skeleton.css"/>">
<link rel="stylesheet" href="<c:url value="/css/custom.css"/>">

<!-- JavaScript
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<!-- embed jQuery javascript file here -->
<script src="<c:url value="/js/jquery-1.11.3.min.js"/>"
	type="text/javascript"></script>


<!-- Favicon
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
<link rel="icon" type="image/png"
	href="<c:url value="/images/favicon.png"/>">

</head>
<body>

	<!-- Primary Page Layout
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
	<div class="container">
		<section class="header">
			<h1>Node Detail</h1>
			<%@ include file="/WEB-INF/views/header.jsp"%>
		</section>
		<div class="docs-section">
			<!-- Inflate search results here -->
			<div class="row" style="text-align: center;">
				<h4>Current node name is</h4>
				<h1>${node.name}</h1>
				<c:choose>
					<c:when test="${isThatHouse == 'YES'}">
						<c:choose>
							<c:when test="${subscription==true}">
								<a class="button button-primary on" id="subscribe"
									href="<c:url value='/Node/${node.nid}/Unsubscribe'/>">On
									subscription</a>
								<p></p>
								<h5>Emergency Recipients</h5>
								<ul id="recipients">
									<c:forEach items="${recipients}" var="recipient"
										varStatus="status">
										<li>${recipient}<a
											href="<c:url value='/Node/${node.nid}/Remove/Recipient/${status.index}'/>">
												-</a></li>
									</c:forEach>
								</ul>

								<form action="<c:url value="/Node/${node.nid}/Add/Recipient"/>"
									method="post">
									<input id="recipientname" name="recipientname" type="number"
										placeholder="1112223333"> <input class="button"
										type="submit" value="Add recipient(Phone#)">
								</form>

								<div class="row">
									<div class="six columns">
										<form
											action="<c:url value="/Node/${node.nid}/Light/Duration"/>"
											method="post">
											<input id="lightDuration" name="lightDuration" type="number"
												placeholder="5" value="${lightOffDuration}"><input
												class="button" type="submit"
												value="Set light off duration(sec)">
										</form>
									</div>
									<div class="six columns">

										<form
											action="<c:url value="/Node/${node.nid}/Warning/Duration"/>"
											method="post">
											<input id="warningDuration" name="warningDuration"
												type="number" placeholder="5" value="${warningDuration}">
											<input class="button" type="submit"
												value="Set warning duration(sec)">
										</form>
									</div>
								</div>
							</c:when>
							<c:otherwise>
								<a class="button" id="subscribe"
									href="<c:url value='/Node/${node.nid}/Subscribe'/>">Subscribe</a>
							</c:otherwise>
						</c:choose>
						<p></p>
						<h2>It's a house, isn't it?</h2>
						<div>
							<h4 id="status">Home status:</h4>
							<div class="row">
								<div class="six columns">
									<h5 id="temperature">Temperature:</h5>
									<h5 id="humidity">Humidity:</h5>
								</div>
								<div class="six columns">
									<h5 id="occupation">Occupation:</h5>
									<h5 id="doorSwitch">Door switch:</h5>
								</div>
							</div>
						</div>

						<div>
							<a class="button" id="door">Open the door</a>

							<script>
								$("#door")
										.click(
												function() {
													$("#door")
															.text(
																	"Processing Request");
													if (!$("#door").hasClass(
															"opened")) {

														$
																.ajax({
																	url : "<c:url value='/Node/${node.nid}/Door/Open'/>",
																	type : "GET",
																	contentType : "application/json",
																	mimeType : "application/json",
																	success : function(
																			resp) {
																		if (JSON
																				.parse(resp.data).result == 'invalid') {
																			alert("Invalid request.");
																		}
																	},
																	error : function() {
																	}
																});
													} else {
														$
																.ajax({
																	url : "<c:url value='/Node/${node.nid}/Door/Close'/>",
																	type : "GET",
																	contentType : "application/json",
																	mimeType : "application/json",
																	success : function(
																			resp) {
																		if (JSON
																				.parse(resp.data).result == 'invalid') {
																			alert("Invalid request.");
																		}
																	},
																	error : function() {
																	}
																});
													}

												});
							</script>



							<a class="button" id="alarm">Turn the alarm on</a>

							<script>
								$("#alarm")
										.click(
												function() {
													$("#alarm")
															.text(
																	"Processing Request");
													if (!$("#alarm").hasClass(
															"on")) {

														$
																.ajax({
																	url : "<c:url value='/Node/${node.nid}/Alarm/On'/>",
																	type : "GET",
																	contentType : "application/json",
																	mimeType : "application/json",
																	success : function(
																			resp) {
																		if (JSON
																				.parse(resp.data).result == 'invalid') {
																			alert("Invalid request.");
																		}
																	},
																	error : function() {
																	}
																});
													} else {
														$
																.ajax({
																	url : "<c:url value='/Node/${node.nid}/Alarm/Off'/>",
																	type : "GET",
																	contentType : "application/json",
																	mimeType : "application/json",
																	success : function(
																			resp) {
																		if (JSON
																				.parse(resp.data).result == 'invalid') {
																			alert("Invalid request.");
																		}
																	},
																	error : function() {
																	}
																});
													}

												});
							</script>
							<a class="button" id="light">Turn the light on</a>

							<script>
								$("#light")
										.click(
												function() {
													$("#light")
															.text(
																	"Processing Request");
													if (!$("#light").hasClass(
															"on")) {

														$
																.ajax({
																	url : "<c:url value='/Node/${node.nid}/Light/On'/>",
																	type : "GET",
																	contentType : "application/json",
																	mimeType : "application/json",
																	success : function(
																			resp) {
																		if (JSON
																				.parse(resp.data).result == 'invalid') {
																			alert("Invalid request.");
																		}
																	},
																	error : function() {
																	}
																});
													} else {
														$
																.ajax({
																	url : "<c:url value='/Node/${node.nid}/Light/Off'/>",
																	type : "GET",
																	contentType : "application/json",
																	mimeType : "application/json",
																	success : function(
																			resp) {
																		if (JSON
																				.parse(resp.data).result == 'invalid') {
																			alert("Invalid request.");
																		}
																	},
																	error : function() {
																	}
																});
													}
												});
							</script>
						</div>

						<script>
							isOnUpdate = false;
							$(document)
									.ready(
											function() {

												window
														.setInterval(
																function() {
																	if (!isOnUpdate) {
																		isOnUpdate = true;
																		$
																				.ajax({
																					url : "<c:url value='/Node/${node.nid}/homeState'/>",
																					type : "GET",
																					contentType : "application/json",
																					mimeType : "application/json",
																					success : function(
																							resp) {
																						//resp["result"]
																						data = JSON
																								.parse(resp["data"]);
																						saValues = JSON
																								.parse(data[0]["savalues"]);
																						status = saValues.status;
																						dat = saValues.dat;

																						if (status == "safe")
																							$(
																									"#status")
																									.text(
																											"Home status: Safe");
																						else if (status == "warn")
																							$(
																									"#status")
																									.text(
																											"Home status: Warning");
																						else if (status == "emer")
																							$(
																									"#status")
																									.text(
																											"Home status: Emergency");

																						dsw = dat.dsw; //door switch
																						if (dsw == 'o')
																							$(
																									"#doorSwitch")
																									.text(
																											"Door switch: ON");
																						else if (dsw == 'x')
																							$(
																									"#doorSwitch")
																									.text(
																											"Door switch: OFF");

																						dsv = dat.dsv; //door servo
																						if (dsv == 'o') {
																							$(
																									"#door")
																									.text(
																											"The door is currently opened.");
																							$(
																									"#door")
																									.addClass(
																											"opened");
																							$(
																									"#door")
																									.addClass(
																											"button-primary");
																						} else if (dsv == 'c') {
																							$(
																									"#door")
																									.text(
																											"The door is currently closed.");
																							$(
																									"#door")
																									.removeClass(
																											"opened");
																							$(
																									"#door")
																									.removeClass(
																											"button-primary");
																						}

																						alr = dat.alr; //alarm
																						if (alr == 'o') {
																							$(
																									"#alarm")
																									.text(
																											"The alarm is currently turned on.");
																							$(
																									"#alarm")
																									.addClass(
																											"on");
																							$(
																									"#alarm")
																									.addClass(
																											"button-primary");
																						} else if (alr == 'x') {
																							$(
																									"#alarm")
																									.text(
																											"The alarm is currently turned off.");
																							$(
																									"#alarm")
																									.removeClass(
																											"on");
																							$(
																									"#alarm")
																									.removeClass(
																											"button-primary");
																						}

																						occ = dat.occ; //occupied
																						if (occ == 'o') {
																							$(
																									"#occupation")
																									.text(
																											"Occupation: Not vacant.");
																						} else if (occ == 'v') {
																							$(
																									"#occupation")
																									.text(
																											"Occupation: Vacant.");
																						}

																						lgt = dat.lgt; //light
																						if (lgt == 'o') {
																							$(
																									"#light")
																									.text(
																											"The light is currently turned on.");
																							$(
																									"#light")
																									.addClass(
																											"on");
																							$(
																									"#light")
																									.addClass(
																											"button-primary");
																						} else if (lgt == 'x') {
																							$(
																									"#light")
																									.text(
																											"The light is currently turned off.");
																							$(
																									"#light")
																									.removeClass(
																											"on");
																							$(
																									"#light")
																									.removeClass(
																											"button-primary");
																						}

																						hum = dat.hum; //humidity
																						$(
																								"#humidity")
																								.text(
																										"Humidity: "
																												+ hum);

																						tmp = dat.tmp; //temperature
																						$(
																								"#temperature")
																								.text(
																										"Temperature: "
																												+ tmp);

																						isOnUpdate = false;
																					},
																					error : function() {
																						isOnUpdate = false;
																					}
																				});

																	}

																}, 3000);

											});
						</script>


					</c:when>
					<c:otherwise>
						<h3>It doesn't look like a house. Or it is not currently
							online.</h3>

					</c:otherwise>
				</c:choose>



			</div>

		</div>
	</div>

	<!-- End Document
  –––––––––––––––––––––––––––––––––––––––––––––––––– -->
</body>
</html>
