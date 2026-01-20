import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "../styles/aboutMe.module.css";
import "../styles/ContactForm.css";
import elements from "../styles/elements.module.css";
import Navbar from "../components/Navbar.jsx";
import { getVersion, createComment } from "../services/commentService";
import { getUserInfo } from "../services/authService";
import { useForm } from "react-hook-form";
import { getInternalId } from "../services/authService";

export default function Comment() {
    const [loading, setLoading] = useState(true);
    const [user, setUser] = useState({});
    const [version, setVersion] = useState({});
    const { id } = useParams();
    const { register, handleSubmit, formState: { errors }, reset } = useForm();
    const [idUser, setIdUser] = useState(null);

    useEffect(() => {
        init();
    }, []);

    async function init() {
        setUser(getUserInfo());
        const versionData = await getVersion(id);
        setVersion(versionData);
        try {
            const internalId = await getInternalId();
            setIdUser(internalId);
        } catch (err) {
            console.error('Erro ao obter ID:', err);
        } finally {
            setLoading(false);
        }
    }

    const onSubmit = async (data) => {
        console.log("Enviando comentário...");
        console.log("Mensagem: ", data.message);
        console.log("User ID: ", idUser);
        console.log("Version ID: ", id);
        
        try {
            await createComment({
                message: data.message,
                idUser: idUser,
                version: id,
            });
            alert("Comentário enviado com sucesso!");
            reset();
        } catch (err) {
            alert(err.message);
        }
    };

    if (loading) {
        return (
            <div className={styles.container}>
                <Navbar />
                <div className={styles.loading}>
                    <p>Carregando...</p>
                </div>
            </div>
        );
    }

    return (
        <>
            <Navbar />
            <div className={styles.container}>
                <h2>Olá {user.name} gostaríamos de saber qual a sua opinião sobre a Newsletter</h2>
                <h3>Para isso gostaríamos de você nos enviar um comentário contando o que achou da Newsletter no dia</h3>
                <h3>Dia: {version.date_rate}</h3>
                <h3>Nota: {version.average}</h3>
            </div>


            <div className="container">
                <div className="form-wrapper">
                    <div className="background-decoration"></div>
                    <div className="form-content">
                    <div className="header">
                        <h1>Feedback!</h1>
                        <p>Olá {user.name} gostaríamos de saber qual a sua opinião sobre a Newsletter</p>
                        <p>Para isso gostaríamos de você nos enviar um comentário contando o que achou da Newsletter</p>
                        <p>Dia: {version.date_rate}</p>                    </div>
                    
                    <form onSubmit={handleSubmit(onSubmit)}>
                        
                        <input
                        className="textarea-field"
                        placeholder="Digite sua mensagem aqui..."
                        {...register("message", { 
                            required: "Este campo é obrigatório",
                        })}
                        ></input>
                        {errors.message && <span style={{ color: "red" }}>{errors.message.message}</span>}
                        
                        <div className="button-group">
                        <input
                            className="btn btn-submit"
                            type="submit"
                            value="Enviar ➤"
                        />
                        <input
                            className="btn btn-reset"
                            type="reset"
                            value="Resetar ✘"
                        />
                        </div>
                        
                    </form>
                    </div>
                </div>
            </div>

        </>
    );
}