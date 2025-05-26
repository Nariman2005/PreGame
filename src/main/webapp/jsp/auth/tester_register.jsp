<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.pregame.gametesting.beans.RegistrationBean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tester Registration | Game Testing Platform</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>


<section class="register-section">
    <div class="container">
        <h2 class="section-title">Register as a Tester</h2>

        <div class="register-form-container">
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <%
                // Initialize the bean or get it from the request
                RegistrationBean registrationBean = (RegistrationBean)request.getAttribute("registrationBean");
                if (registrationBean == null) {
                    registrationBean = new RegistrationBean();
                    registrationBean.setUserType("tester");
                }
            %>

            <form action="${pageContext.request.contextPath}/auth" method="post" class="register-form">
                <input type="hidden" name="action" value="register">
                <input type="hidden" name="userType" value="tester">

                <div class="form-group">
                    <label for="name">Your Name</label>
                    <input type="text" id="name" name="name" value="<%= registrationBean.getName() != null ? registrationBean.getName() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" value="<%= registrationBean.getEmail() != null ? registrationBean.getEmail() : "" %>" required>
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>

                <div class="form-group">
                    <label for="age">Age</label>
                    <input type="number" id="age" name="age" value="<%= registrationBean.getAge() > 0 ? registrationBean.getAge() : "" %>" min="1" required>
                </div>

                <div class="form-row">
                    <div class="form-group half">
                        <label for="countryCode">Country Code</label>
                        <select id="countryCode" name="countryCode" required>
                            <option value="+1" <%= "+1".equals(registrationBean.getCountryCode()) ? "selected" : "" %>>United States (+1)</option>
                            <option value="+20" <%= "+20".equals(registrationBean.getCountryCode()) ? "selected" : "" %>>Egypt (+20)</option>
                            <option value="+44" <%= "+44".equals(registrationBean.getCountryCode()) ? "selected" : "" %>>United Kingdom (+44)</option>
                            <option value="+33" <%= "+33".equals(registrationBean.getCountryCode()) ? "selected" : "" %>>France (+33)</option>
                            <option value="+49" <%= "+49".equals(registrationBean.getCountryCode()) ? "selected" : "" %>>Germany (+49)</option>
                        </select>
                    </div>

                    <div class="form-group half">
                        <label for="telephone">Telephone Number</label>
                        <input type="tel" id="telephone" name="telephone" value="<%= registrationBean.getTelephone() != null ? registrationBean.getTelephone() : "" %>" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="rank">Testing Rank</label>
                    <select id="rank" name="rank" required>
                        <option value="Novice" <%= "Novice".equals(registrationBean.getRank()) ? "selected" : "" %>>Novice</option>
                        <option value="Intermediate" <%= "Intermediate".equals(registrationBean.getRank()) ? "selected" : "" %>>Intermediate</option>
                        <option value="Advanced" <%= "Advanced".equals(registrationBean.getRank()) ? "selected" : "" %>>Advanced</option>
                        <option value="Expert" <%= "Expert".equals(registrationBean.getRank()) ? "selected" : "" %>>Expert</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary">Register</button>

                <div class="form-footer">
                    <p>Already have an account? <a href="${pageContext.request.contextPath}/auth?action=login">Log In</a></p>
                    <p>Register as a:
                        <a href="${pageContext.request.contextPath}/auth?action=register&type=gamer">Gamer</a> |
                        <a href="${pageContext.request.contextPath}/auth?action=register&type=developer">Developer</a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</section>



</body>
</html>
