<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include Header -->

<!-- Main Content -->
<main>
    <!-- Hero Section -->
    <section style="background: linear-gradient(135deg, #3a6df0, #5c86f6); padding: 3rem 0; position: relative;">
        <div class="container">
            <div style="text-align: center; max-width: 800px; margin: 0 auto; color: white;">
                <h1 style="font-size: 2.5rem; font-weight: 700; margin-bottom: 1rem;">Contact Us</h1>
                <p style="font-size: 1.2rem; opacity: 0.9;">Have questions or need assistance? We're here to help!</p>
            </div>
        </div>
    </section>

    <!-- Contact Content -->
    <section style="padding: 4rem 0; background-color: white;">
        <div class="container">
            <div style="display: flex; flex-wrap: wrap; gap: 3rem; margin-bottom: 3rem;">
                <!-- Contact Form -->
                <div style="flex: 1.5; min-width: 300px;">
                    <h2 style="font-size: 1.8rem; margin-bottom: 1.5rem; color: #313e5b; position: relative;">
                        Get in Touch
                        <span style="display: block; width: 50px; height: 4px; background: #3a6df0; margin-top: 0.5rem;"></span>
                    </h2>

                    <!-- Success and Error Messages -->
                    <% if (request.getAttribute("successMessage") != null) { %>
                        <div style="padding: 1rem; background-color: rgba(0, 200, 83, 0.1); border-left: 4px solid #00c853; border-radius: 4px; margin-bottom: 1.5rem;">
                            <p style="margin: 0; color: #00813e;"><i class="fas fa-check-circle" style="margin-right: 0.5rem;"></i> <%= request.getAttribute("successMessage") %></p>
                        </div>
                    <% } %>

                    <% if (request.getAttribute("errorMessage") != null) { %>
                        <div style="padding: 1rem; background-color: rgba(244, 67, 54, 0.1); border-left: 4px solid #f44336; border-radius: 4px; margin-bottom: 1.5rem;">
                            <p style="margin: 0; color: #d32f2f;"><i class="fas fa-exclamation-circle" style="margin-right: 0.5rem;"></i> <%= request.getAttribute("errorMessage") %></p>
                        </div>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/contact" method="post" style="margin-top: 2rem;">
                        <div style="margin-bottom: 1.5rem;">
                            <label for="name" style="display: block; margin-bottom: 0.5rem; font-weight: 500; color: #313e5b;">Full Name</label>
                            <input type="text" id="name" name="name" required
                                   style="width: 100%; padding: 0.8rem; border: 1px solid #d1d9e6; border-radius: 8px; background: #f9faff; font-size: 1rem; outline: none;">
                        </div>

                        <div style="margin-bottom: 1.5rem;">
                            <label for="email" style="display: block; margin-bottom: 0.5rem; font-weight: 500; color: #313e5b;">Email Address</label>
                            <input type="email" id="email" name="email" required
                                   style="width: 100%; padding: 0.8rem; border: 1px solid #d1d9e6; border-radius: 8px; background: #f9faff; font-size: 1rem; outline: none;">
                        </div>

                        <div style="margin-bottom: 1.5rem;">
                            <label for="subject" style="display: block; margin-bottom: 0.5rem; font-weight: 500; color: #313e5b;">Subject</label>
                            <select id="subject" name="subject" required
                                    style="width: 100%; padding: 0.8rem; border: 1px solid #d1d9e6; border-radius: 8px; background: #f9faff; font-size: 1rem; outline: none;">
                                <option value="">Select a subject</option>
                                <option value="general">General Inquiry</option>
                                <option value="support">Technical Support</option>
                                <option value="partnership">Partnership Opportunity</option>
                                <option value="feedback">Feedback</option>
                            </select>
                        </div>

                        <div style="margin-bottom: 1.5rem;">
                            <label for="message" style="display: block; margin-bottom: 0.5rem; font-weight: 500; color: #313e5b;">Message</label>
                            <textarea id="message" name="message" rows="6" required
                                     style="width: 100%; padding: 0.8rem; border: 1px solid #d1d9e6; border-radius: 8px; background: #f9faff; font-size: 1rem; outline: none; resize: vertical;"></textarea>
                        </div>

                        <button type="submit"
                                style="background: linear-gradient(135deg, #3a6df0, #5c86f6); color: white; border: none; padding: 0.8rem 2rem; border-radius: 8px; font-weight: 600; cursor: pointer; box-shadow: 0 5px 15px rgba(58, 109, 240, 0.3); display: inline-flex; align-items: center;">
                            <i class="fas fa-paper-plane" style="margin-right: 8px;"></i> Send Message
                        </button>
                    </form>
                </div>

                <!-- Contact Information -->
                <div style="flex: 1; min-width: 300px;">
                    <h2 style="font-size: 1.8rem; margin-bottom: 1.5rem; color: #313e5b; position: relative;">
                        Contact Information
                        <span style="display: block; width: 50px; height: 4px; background: #3a6df0; margin-top: 0.5rem;"></span>
                    </h2>

                    <div style="background: #f9faff; border-radius: 10px; padding: 2rem; box-shadow: 0 5px 20px rgba(0,0,0,0.05);">
                        <div style="display: flex; align-items: flex-start; margin-bottom: 1.5rem;">
                            <div style="background: rgba(58, 109, 240, 0.1); width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-right: 1rem; flex-shrink: 0;">
                                <i class="fas fa-map-marker-alt" style="color: #3a6df0;"></i>
                            </div>
                            <div>
                                <h3 style="font-size: 1.2rem; margin-bottom: 0.5rem; color: #313e5b;">Our Location</h3>
                                <p style="color: #6e7891; line-height: 1.6;">12 El-Tahrir Square</p>
                            </div>
                        </div>

                        <div style="display: flex; align-items: flex-start; margin-bottom: 1.5rem;">
                            <div style="background: rgba(58, 109, 240, 0.1); width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-right: 1rem; flex-shrink: 0;">
                                <i class="fas fa-phone-alt" style="color: #3a6df0;"></i>
                            </div>
                            <div>
                                <h3 style="font-size: 1.2rem; margin-bottom: 0.5rem; color: #313e5b;">Call Us</h3>
                                <p style="color: #6e7891; line-height: 1.6;">+16500</p>
                            </div>
                        </div>

                        <div style="display: flex; align-items: flex-start; margin-bottom: 1.5rem;">
                            <div style="background: rgba(58, 109, 240, 0.1); width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-right: 1rem; flex-shrink: 0;">
                                <i class="fas fa-envelope" style="color: #3a6df0;"></i>
                            </div>
                            <div>
                                <h3 style="font-size: 1.2rem; margin-bottom: 0.5rem; color: #313e5b;">Email Us</h3>
                                <p style="color: #6e7891; line-height: 1.6;">PreGameDevs@gmail.com</p>
                            </div>
                        </div>

                        <div style="display: flex; align-items: flex-start;">
                            <div style="background: rgba(58, 109, 240, 0.1); width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-right: 1rem; flex-shrink: 0;">
                                <i class="fas fa-clock" style="color: #3a6df0;"></i>
                            </div>
                            <div>
                                <h3 style="font-size: 1.2rem; margin-bottom: 0.5rem; color: #313e5b;">Working Hours</h3>
                                <p style="color: #6e7891; line-height: 1.6;">Monday - Friday: 9:00 AM - 5:00 PM</p>
                                <p style="color: #6e7891; line-height: 1.6;">Weekend: Closed</p>
                            </div>
                        </div>
                    </div>

                    <!-- Social Media -->
                    <div style="margin-top: 2rem;">
                        <h3 style="font-size: 1.2rem; margin-bottom: 1rem; color: #313e5b;">Follow Us</h3>
                        <div style="display: flex; gap: 1rem;">
                            <a href="#" aria-label="Facebook" style="background: white; width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s;">
                                <i class="fab fa-facebook-f" style="color: #3a6df0;"></i>
                            </a>
                            <a href="#" aria-label="Twitter" style="background: white; width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s;">
                                <i class="fab fa-twitter" style="color: #3a6df0;"></i>
                            </a>
                            <a href="#" aria-label="Instagram" style="background: white; width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s;">
                                <i class="fab fa-instagram" style="color: #3a6df0;"></i>
                            </a>
                            <a href="#" aria-label="LinkedIn" style="background: white; width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 4px 8px rgba(0,0,0,0.05); transition: transform 0.2s;">
                                <i class="fab fa-linkedin-in" style="color: #3a6df0;"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Map -->
            <div style="margin-top: 4rem; border-radius: 10px; overflow: hidden; box-shadow: 0 5px 20px rgba(0,0,0,0.05);">
                <iframe
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3022.922512351711!2d-73.98779568531606!3d40.74844097932861!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c259a9b3117469%3A0xd134e199a405a163!2sEmpire%20State%20Building!5e0!3m2!1sen!2sus!4v1621538241472!5m2!1sen!2sus"
                    width="100%"
                    height="450"
                    style="border:0;"
                    allowfullscreen=""
                    loading="lazy">
                </iframe>
            </div>
        </div>
    </section>
</main>

<!-- Include Footer -->

