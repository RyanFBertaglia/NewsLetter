const styles = () => {
return styles (
    <>
    minHeight: '100vh',
        background: 'linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%)',
        fontFamily: '-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif'
    },
    nav: {
        display: 'grid',
        gridTemplateColumns: 'auto 1fr auto',
        alignItems: 'center',
        padding: '1rem 1.5rem',
        background: 'white',
        boxShadow: '0 1px 3px rgba(0, 0, 0, 0.1)'
    },
    navLogo: {
        display: 'flex',
        alignItems: 'center'
    },
    logoCircle: {
        width: '32px',
        height: '32px',
        background: '#ef4444',
        borderRadius: '50%',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center'
    },
    navCenter: {
        display: 'flex',
        justifyContent: 'center',
        gap: '2rem'
    },
    navLink: {
        color: '#6b7280',
        textDecoration: 'none',
        transition: 'color 0.3s ease'
    },
    navActions: {
        display: 'grid',
        gridTemplateColumns: 'auto auto',
        gap: '0.75rem',
        alignItems: 'center'
    },
    btnLogin: {
        background: '#f0fdf4',
        color: '#166534',
        padding: '0.5rem 1rem',
        borderRadius: '0.5rem',
        border: 'none',
        cursor: 'pointer',
        transition: 'background 0.3s ease',
        fontWeight: '500'
    },
    btnRegister: {
        background: '#374151',
        color: 'white',
        padding: '0.5rem 1rem',
        borderRadius: '0.5rem',
        border: 'none',
        cursor: 'pointer',
        transition: 'background 0.3s ease',
        fontWeight: '500'
    },
    mainContent: {
        display: 'grid',
        placeItems: 'center',
        minHeight: 'calc(100vh - 80px)',
        padding: '2rem',
        position: 'relative',
        overflow: 'hidden'
    },
    contentWrapper: {
        display: 'grid',
        gridTemplateRows: 'auto auto auto',
        gap: '3rem',
        textAlign: 'center',
        maxWidth: '32rem',
        zIndex: 10
    },
    titleSection: {
        display: 'grid',
        gap: '1.5rem'
    },
    title: {
        fontSize: '4rem',
        fontWeight: '700',
        color: '#ef4444',
        lineHeight: '1.1',
        margin: 0
    },
    subtitle: {
        fontSize: '1.25rem',
        color: '#6b7280',
        lineHeight: '1.6',
        margin: 0
    },
    buttonActions: {
        display: 'grid',
        gridTemplateColumns: 'auto auto',
        gap: '1rem',
        justifyContent: 'center'
    },
    btnPrimary: {
        background: '#ef4444',
        color: 'white',
        fontWeight: '600',
        padding: '0.75rem 2rem',
        borderRadius: '0.5rem',
        border: 'none',
        cursor: 'pointer',
        transition: 'all 0.3s ease',
        transformOrigin: 'center'
    },
    btnSecondary: {
        background: '#374151',
        color: 'white',
        fontWeight: '600',
        padding: '0.75rem 1.5rem',
        borderRadius: '0.5rem',
        border: 'none',
        cursor: 'pointer',
        transition: 'all 0.3s ease',
        display: 'flex',
        alignItems: 'center',
        gap: '0.5rem',
        transformOrigin: 'center'
    },
    decoration1: {
        position: 'absolute',
        top: '5rem',
        left: '5rem',
        width: '8rem',
        height: '8rem',
        background: '#bbf7d0',
        borderRadius: '50%',
        opacity: 0.3,
        animation: 'pulse 3s ease-in-out infinite'
    },
    decoration2: {
        position: 'absolute',
        bottom: '5rem',
        right: '5rem',
        width: '6rem',
        height: '6rem',
        background: '#fecaca',
        borderRadius: '50%',
        opacity: 0.3,
        animation: 'pulse 3s ease-in-out infinite',
        animationDelay: '1s'
    },
    decoration3: {
        position: 'absolute',
        top: '50%',
        left: '2.5rem',
        width: '4rem',
        height: '4rem',
        background: '#bfdbfe',
        borderRadius: '50%',
        opacity: 0.3,
        animation: 'pulse 3s ease-in-out infinite',
        animationDelay: '2s'
    },
    successMessage: {
        position: 'fixed',
        top: '1rem',
        right: '1rem',
        background: '#10b981',
        color: 'white',
        padding: '0.75rem 1.5rem',
        borderRadius: '0.5rem',
        boxShadow: '0 10px 25px rgba(0, 0, 0, 0.1)',
        zIndex: 50
}
</>
);
};
export default styles;