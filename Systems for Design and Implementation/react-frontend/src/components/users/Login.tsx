import { useState } from "react";
import { BACKEND_URL } from "../../utils";
import { createBrowserHistory } from "history";
import { Button, Container, IconButton } from "@mui/material";
import { Form } from "react-bootstrap";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Link } from "react-router-dom";


export function Login({ handleLogin }: { handleLogin: (username: string, id: number) => void }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        fetch(`${BACKEND_URL}/login/`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                password,
                username,
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.user) {
                    const history = createBrowserHistory();
                    history.push('/users/' + data.user.id);
                    window.location.reload();
                    handleLogin(data.user.userName, data.user.id);
                } else {
                    alert('Login failed');
                }
            });
    };

    return (
        <Container
            style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                height: '65vh',
            }}
        >
            <IconButton component={Link} sx={{ mr: 3 }} to={`/`}>
				<ArrowBackIcon />
			</IconButton>
            <h1>Welcome back!</h1>
            <Form onSubmit={handleSubmit} style={{display: "flex", flexDirection: "column", padding: "8px"}}>
                <Form.Group controlId="username">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        type="text"
                        name="username"
                        value={username}
                        onChange={(event) => setUsername(event.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        name="password"
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                        required
                    />
                </Form.Group>
                <div
                    style={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center',
                        height: '8vh',
                    }}
                >
                    <Button variant="contained" type="submit" disabled={!username || !password}>
                        Log in
                    </Button>
                </div>
            </Form>
        </Container>
    );
}