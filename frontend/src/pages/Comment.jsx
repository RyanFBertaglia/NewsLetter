import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "../styles/aboutMe.module.css";
import elements from "../styles/elements.module.css";
import Navbar from "../components/Navbar.jsx";
import { getVersion, createComment } from "../services/commentService";
import { getUserInfo } from "../services/authService";

export default function Comment() {

    const [loading, setLoading] = useState(true);
    const [user, setUser] = useState({});
    const { id } = useParams();

    useEffect(()=> {
        init();
    }, [id]);

    async function init() {
        console.log("user info: ");
        console.log(getUserInfo());
        const version = await getVersion(id);
        console.log(version);
        setUser(getUserInfo());
        setLoading(false);
    }


    /*async function handleSend(e) {
        e.preventDefault();
        try {
            await createComment({
            message,
            idUser: userId,
            version: id,
        });
        alert("Comentário enviado com sucesso!");
        setMessage("");
        } catch (err) {
            alert(err.message);
        }
    }*/

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
            <h1>{user.name}</h1>
            <p>Dia: {version}</p>
            <p>{user.email}</p>
            <p>{user.id}</p>
        </div>
        </>
    );
}