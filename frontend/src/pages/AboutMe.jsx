import React from "react";
import styles from "../styles/aboutMe.module.css";
import elements from "../styles/elements.module.css";
import Navbar from "../components/Navbar.jsx";

export default function AboutMe() {
  return (
    <>
      <Navbar />
      <section className={styles.aboutSection}>
        <div className={styles.aboutWrapper}>
          <div className={styles.profileCircle}>
            <span className={styles.initials}>RFB</span>
          </div>

          <h2 className={styles.aboutTitle}>Sobre mim</h2>

          <p className={styles.aboutText}>
            Olá! 👋 Sou <strong>Ryan Fernandes Bertaglia</strong>, um
            desenvolvedor web de 16 anos apaixonado por tecnologia e
            inovação. Atualmente estudo na{" "}
            <strong>Fundação Bradesco</strong> e desenvolvo projetos que sejam{" "}
            <strong>robustos</strong> mantendo{" "}
            <strong>interfaces modernas</strong>, usando tecnologias como{" "}
            <strong>Java, Spring Boot, PHP, TypeScript e React</strong>.
          </p>

          <p className={styles.aboutText}>
            Além de programar, estou sempre buscando evoluir como profissional,
            participando de iniciativas como a{" "}
            <strong>OBI (Olimpíada Brasileira de Informática)</strong> e obtendo
            certificações como <strong>AZ-900</strong> e{" "}
            <strong>PL-900</strong> da Microsoft,{" "}
            <strong>AWS Cloud Practitioner Foundations</strong> da AWS e{" "}
            <strong>Java Foundations</strong> da Oracle. Meu objetivo é construir
            soluções digitais para problemas reais. 🚀
          </p>

            <div className={styles.aboutButtons}>
              <a
                href="https://github.com/RyanFBertaglia"
                className={elements.btnPrimary}
                target="_blank"
                rel="noopener noreferrer"
              >
                Ver Projetos
              </a>

              <a
                href="https://www.linkedin.com/in/ryan-fernandes-bertaglia-943aa6302"
                className={elements.btnSecondary}
                target="_blank"
                rel="noopener noreferrer"
              >
                LinkedIn
              </a>
            </div>
        </div>
      </section>
    </>
  );
}
