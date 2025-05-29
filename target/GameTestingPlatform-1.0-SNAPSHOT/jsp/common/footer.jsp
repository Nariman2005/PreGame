<%-- footer.jsp --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%--
    This footer is designed to be included in other JSP pages.
    It uses Font Awesome for icons (ensure Font Awesome is linked in your main HTML <head>).
    It also references a global script.js file.
--%>

<footer style="background: linear-gradient(to bottom, #f5f8ff, #edf1fc); padding: 4rem 0 0; color: #313e5b; position: relative; margin-top: auto; /* Pushes footer to bottom if content is short */">
    <!-- Decorative wave shape at the top -->
    <div style="position: absolute; top: 0; left: 0; width: 100%; height: 50px; overflow: hidden; pointer-events: none;">
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320" style="position: absolute; top: -200px; left: 0; width: 100%; height: auto;">
            <path fill="#ffffff" fill-opacity="1" d="M0,160L48,165.3C96,171,192,181,288,170.7C384,160,480,128,576,128C672,128,768,160,864,170.7C960,181,1056,171,1152,144C1248,117,1344,75,1392,53.3L1440,32L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path>
        </svg>
    </div>

    <div class="container"> <%-- Ensure .container class is defined in your global CSS for consistent width and centering --%>
        <div style="display: flex; flex-wrap: wrap; gap: 2rem; margin-bottom: 3rem;">

            <!-- Brand Column -->
            <div style="flex: 2; min-width: 250px;">
                <div style="margin-bottom: 1.5rem;">
                    <h2 style="font-size: 1.8rem; font-weight: 700; background: linear-gradient(135deg, #3a6df0, #5c86f6); -webkit-background-clip: text; -webkit-text-fill-color: transparent; margin-bottom: 0.5rem;">PreGame Testing</h2>
                    <div style="width: 50px; height: 4px; background: linear-gradient(135deg, #3a6df0, #5c86f6); border-radius: 2px; margin-bottom: 1rem;"></div>
                </div>
                <p style="margin-bottom: 1.5rem; font-size: 1rem; line-height: 1.6; color: #6e7891;">The premier platform connecting game developers with testers and gamers to create better gaming experiences. Our mission is to elevate the quality of games through collaborative testing.</p>

                <div style="margin-top: 1.5rem;">
                    <p style="font-weight: 600; margin-bottom: 0.5rem; color: #313e5b;">Connect With Us</p>
                    <div style="display: flex; gap: 0.75rem;">
                        <a href="#" aria-label="Facebook" style="background: white; width: 38px; height: 38px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s ease, box-shadow 0.2s ease;"><i class="fab fa-facebook-f" style="color: #3a6df0;"></i></a>
                        <a href="#" aria-label="Twitter" style="background: white; width: 38px; height: 38px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s ease, box-shadow 0.2s ease;"><i class="fab fa-twitter" style="color: #3a6df0;"></i></a>
                        <a href="#" aria-label="Instagram" style="background: white; width: 38px; height: 38px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s ease, box-shadow 0.2s ease;"><i class="fab fa-instagram" style="color: #3a6df0;"></i></a>
                        <a href="#" aria-label="LinkedIn" style="background: white; width: 38px; height: 38px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s ease, box-shadow 0.2s ease;"><i class="fab fa-linkedin-in" style="color: #3a6df0;"></i></a>
                        <a href="#" aria-label="Discord" style="background: white; width: 38px; height: 38px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s ease, box-shadow 0.2s ease;"><i class="fab fa-discord" style="color: #3a6df0;"></i></a>
                    </div>
                </div>
            </div>

            <!-- Quick Links Column -->
            <div style="flex: 1; min-width: 160px;">
                <h3 style="font-size: 1.2rem; font-weight: 600; margin-bottom: 1.5rem; position: relative; color: #313e5b;">Quick Links</h3>
                <ul style="list-style: none; padding: 0;">
                    <li style="margin-bottom: 0.8rem;"><a href="${pageContext.request.contextPath}/" style="color: #6e7891; text-decoration: none; transition: color 0.2s ease; display: flex; align-items: center;"><i class="fas fa-chevron-right" style="font-size: 0.75rem; margin-right: 0.5rem; color: #3a6df0;"></i> Home</a></li>
                    <li style="margin-bottom: 0.8rem;"><a href="${pageContext.request.contextPath}/games/browse" style="color: #6e7891; text-decoration: none; transition: color 0.2s ease; display: flex; align-items: center;"><i class="fas fa-chevron-right" style="font-size: 0.75rem; margin-right: 0.5rem; color: #3a6df0;"></i> Games</a></li>
                    <li style="margin-bottom: 0.8rem;"><a href="${pageContext.request.contextPath}/about" style="color: #6e7891; text-decoration: none; transition: color 0.2s ease; display: flex; align-items: center;"><i class="fas fa-chevron-right" style="font-size: 0.75rem; margin-right: 0.5rem; color: #3a6df0;"></i> About</a></li>
                    <li style="margin-bottom: 0.8rem;"><a href="${pageContext.request.contextPath}/contact" style="color: #6e7891; text-decoration: none; transition: color 0.2s ease; display: flex; align-items: center;"><i class="fas fa-chevron-right" style="font-size: 0.75rem; margin-right: 0.5rem; color: #3a6df0;"></i> Contact</a></li>
                </ul>
            </div>

            <!-- For Gamers Column -->
            <div style="flex: 1; min-width: 160px;">
                <h3 style="font-size: 1.2rem; font-weight: 600; margin-bottom: 1.5rem; color: #313e5b;">For Gamers</h3>
                <ul style="list-style: none; padding: 0;">
                    <li style="margin-bottom: 0.8rem;"><a href="${pageContext.request.contextPath}/games/upcoming" style="color: #6e7891; text-decoration: none; transition: color 0.2s ease; display: flex; align-items: center;"><i class="fas fa-chevron-right" style="font-size: 0.75rem; margin-right: 0.5rem; color: #3a6df0;"></i> Upcoming Games</a></li>
                    <li style="margin-bottom: 0.8rem;"><a href="${pageContext.request.contextPath}/games/testing" style="color: #6e7891; text-decoration: none; transition: color 0.2s ease; display: flex; align-items: center;"><i class="fas fa-chevron-right" style="font-size: 0.75rem; margin-right: 0.5rem; color: #3a6df0;"></i> Games in Testing</a></li>
                    <li style="margin-bottom: 0.8rem;"><a href="${pageContext.request.contextPath}/gamer/dashboard" style="color: #6e7891; text-decoration: none; transition: color 0.2s ease; display: flex; align-items: center;"><i class="fas fa-chevron-right" style="font-size: 0.75rem; margin-right: 0.5rem; color: #3a6df0;"></i> Gamer Dashboard</a></li>
                </ul>
            </div>

            <!-- Newsletter Column -->
            <div style="flex: 1.5; min-width: 250px;">
                <h3 style="font-size: 1.2rem; font-weight: 600; margin-bottom: 1.5rem; color: #313e5b;">Subscribe to Our Newsletter</h3>
                <p style="margin-bottom: 1rem; color: #6e7891;">Stay updated with the latest game releases, testing opportunities, and industry insights.</p>

                <form action="${pageContext.request.contextPath}/subscribe" method="post" style="margin-top: 1rem;">
                    <div style="position: relative;">
                        <input type="email" name="email" placeholder="Your email address" required style="width: 100%; padding: 0.8rem 1rem; padding-right: 3.5rem; border: 1px solid #d1d9e6; border-radius: 8px; background: white; font-size: 0.95rem; outline: none; box-sizing: border-box;">
                        <button type="submit" aria-label="Subscribe" style="position: absolute; right: 5px; top: 50%; transform: translateY(-50%); background: linear-gradient(135deg, #3a6df0, #5c86f6); color: white; border: none; border-radius: 6px; width: 38px; height: 38px; display: flex; align-items: center; justify-content: center; cursor: pointer;">
                            <i class="fas fa-paper-plane"></i>
                        </button>
                    </div>
                </form>

                <div style="margin-top: 1.5rem;">
                    <p style="font-size: 0.9rem; color: #6e7891;"><i class="fas fa-shield-alt" style="margin-right: 0.5rem; color: #3a6df0;"></i> Your email is safe with us. We don't spam.</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Copyright Section -->
    <div style="background-color: #e9edfb; padding: 1.5rem 0; border-top: 1px solid #d1d9e6;">
        <div class="container">
            <div style="display: flex; flex-wrap: wrap; justify-content: space-between; align-items: center; gap: 1rem;">
                <div>
                    <p style="margin: 0; color: #6e7891; font-size: 0.9rem;">© <%= java.time.Year.now().getValue() %> PreGame Testing Platform. All rights reserved.</p>
                </div>
                <div>
                    <ul style="list-style: none; display: flex; flex-wrap: wrap; gap: 1rem; margin: 0; padding: 0;">
                        <li><a href="${pageContext.request.contextPath}/terms" style="color: #6e7891; text-decoration: none; font-size: 0.9rem; transition: color 0.2s ease;">Terms of Service</a></li>
                        <li><a href="${pageContext.request.contextPath}/privacy" style="color: #6e7891; text-decoration: none; font-size: 0.9rem; transition: color 0.2s ease;">Privacy Policy</a></li>
                        <li><a href="${pageContext.request.contextPath}/cookies" style="color: #6e7891; text-decoration: none; font-size: 0.9rem; transition: color 0.2s ease;">Cookie Policy</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</footer>

<%--
    The following script tag is included as it was in your provided code.
    Generally, global scripts are linked once in the main page (e.g., index.jsp)
    after all content, including the footer, rather than in each included fragment.
    However, if script.js contains functionality specific to the footer, this is acceptable.
--%>
<%-- <script src="${pageContext.request.contextPath}/js/script.js"></script> --%>