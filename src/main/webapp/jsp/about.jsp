<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include Header -->

<!-- Main Content -->
<main>
    <!-- Hero Section -->
    <section style="background: linear-gradient(135deg, #3a6df0, #5c86f6); padding: 3rem 0; position: relative;">
        <div class="container">
            <div style="text-align: center; max-width: 800px; margin: 0 auto; color: white;">
                <h1 style="font-size: 2.5rem; font-weight: 700; margin-bottom: 1rem;">About PreGame Testing</h1>
                <p style="font-size: 1.2rem; opacity: 0.9;">Connecting developers, testers, and gamers for better gaming experiences.</p>
            </div>
        </div>
    </section>

    <!-- About Content -->
    <section style="padding: 4rem 0; background-color: white;">
        <div class="container">
            <div style="display: flex; flex-wrap: wrap; gap: 3rem; align-items: center;">
                <div style="flex: 1; min-width: 300px;">
                    <img src="${pageContext.request.contextPath}/images/about-team.jpg"
                         alt="PreGame Testing Team"
                         style="width: 100%; border-radius: 10px; box-shadow: 0 10px 30px rgba(0,0,0,0.1);"
                         onerror="this.src='https://via.placeholder.com/600x400?text=Our+Team'">
                </div>

                <div style="flex: 1; min-width: 300px;">
                    <h2 style="font-size: 2rem; margin-bottom: 1.5rem; color: #313e5b; position: relative;">
                        Our Mission
                        <span style="display: block; width: 50px; height: 4px; background: #3a6df0; margin-top: 0.5rem;"></span>
                    </h2>

                    <p style="font-size: 1.1rem; line-height: 1.6; color: #6e7891; margin-bottom: 1.5rem;">
                        Founded in 2022, PreGame Testing was created with a simple mission: to improve the quality of video games
                        by connecting game developers directly with passionate testers and gamers before release.
                    </p>

                    <p style="font-size: 1.1rem; line-height: 1.6; color: #6e7891; margin-bottom: 1.5rem;">
                        We believe that great games are built with continuous feedback and testing. Our platform provides the infrastructure
                        for seamless collaboration between those who create games and those who play them.
                    </p>

                    <div style="background: rgba(58, 109, 240, 0.05); border-left: 4px solid #3a6df0; padding: 1.5rem; border-radius: 0 8px 8px 0; margin: 2rem 0;">
                        <p style="font-style: italic; color: #313e5b; margin: 0;">
                            "We're building a community where passion for gaming meets professional development standards, creating better
                            gaming experiences for everyone."
                        </p>
                        <p style="margin: 0.5rem 0 0; font-weight: 600; color: #3a6df0;">— PreGame Testing Founder</p>
                    </div>
                </div>
            </div>

            <!-- What We Do Section -->
            <div style="margin-top: 4rem;">
                <h2 style="font-size: 2rem; text-align: center; margin-bottom: 3rem; color: #313e5b;">
                    What We Do
                    <span style="display: block; width: 50px; height: 4px; background: #3a6df0; margin: 0.5rem auto;"></span>
                </h2>

                <div style="display: flex; flex-wrap: wrap; gap: 2rem; justify-content: center;">
                    <!-- Service 1 -->
                    <div style="flex: 1; min-width: 250px; max-width: 350px; background: white; border-radius: 10px; padding: 2rem; box-shadow: 0 5px 20px rgba(0,0,0,0.05); text-align: center;">
                        <div style="margin-bottom: 1.5rem;">
                            <i class="fas fa-gamepad" style="font-size: 3rem; color: #3a6df0;"></i>
                        </div>
                        <h3 style="margin-bottom: 1rem; color: #313e5b; font-size: 1.3rem;">Game Testing</h3>
                        <p style="color: #6e7891; line-height: 1.6;">
                            We connect game developers with dedicated testers who provide valuable feedback on gameplay, user experience,
                            and bug detection before release.
                        </p>
                    </div>

                    <!-- Service 2 -->
                    <div style="flex: 1; min-width: 250px; max-width: 350px; background: white; border-radius: 10px; padding: 2rem; box-shadow: 0 5px 20px rgba(0,0,0,0.05); text-align: center;">
                        <div style="margin-bottom: 1.5rem;">
                            <i class="fas fa-users" style="font-size: 3rem; color: #3a6df0;"></i>
                        </div>
                        <h3 style="margin-bottom: 1rem; color: #313e5b; font-size: 1.3rem;">Community Building</h3>
                        <p style="color: #6e7891; line-height: 1.6;">
                            We foster connections between passionate gamers, professional testers, and innovative developers to create
                            a thriving ecosystem for game improvement.
                        </p>
                    </div>

                    <!-- Service 3 -->
                    <div style="flex: 1; min-width: 250px; max-width: 350px; background: white; border-radius: 10px; padding: 2rem; box-shadow: 0 5px 20px rgba(0,0,0,0.05); text-align: center;">
                        <div style="margin-bottom: 1.5rem;">
                            <i class="fas fa-chart-line" style="font-size: 3rem; color: #3a6df0;"></i>
                        </div>
                        <h3 style="margin-bottom: 1rem; color: #313e5b; font-size: 1.3rem;">Analytics & Insights</h3>
                        <p style="color: #6e7891; line-height: 1.6;">
                            We provide developers with detailed analytics and tester feedback, helping them make data-driven decisions
                            to improve their games.
                        </p>
                    </div>
                </div>
            </div>

            <!-- Team Section -->
            <div style="margin-top: 5rem; text-align: center;">
                <h2 style="font-size: 2rem; margin-bottom: 1rem; color: #313e5b;">Our Team</h2>
                <p style="max-width: 700px; margin: 0 auto 3rem; color: #6e7891; font-size: 1.1rem;">
                    We're a passionate team of gamers, developers, and industry professionals dedicated to elevating the quality of gaming experiences worldwide.
                </p>

                <!-- Team grid will go here -->
                <div style="text-align: center; margin-top: 2rem;">
                    <a href="${pageContext.request.contextPath}/contact" style="display: inline-block; background: linear-gradient(135deg, #3a6df0, #5c86f6); color: white; padding: 0.8rem 2rem; border-radius: 8px; font-weight: 600; text-decoration: none; box-shadow: 0 5px 15px rgba(58, 109, 240, 0.3);">
                        <i class="fas fa-envelope" style="margin-right: 8px;"></i> Get in Touch
                    </a>
                </div>
            </div>
        </div>
    </section>
</main>

<!-- Include Footer -->

