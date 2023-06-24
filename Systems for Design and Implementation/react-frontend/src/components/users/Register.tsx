import { Component } from "react";
import { BACKEND_URL } from "../../utils";
import { Button, Container, IconButton } from "@mui/material";
import { Form } from "react-bootstrap";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import { Link} from "react-router-dom";


type RegisterState = {
    password: string;
    passwordConfirm: string;
    username: string;
    email: string;
    first_name: string;
    last_name: string;
    bio: string;
    location: string;
    gender: string;
    currentStep: number;
  };
  


export class Register extends Component<{}, RegisterState> {
    constructor(props: {}) {
        super(props);

        this.state = {
            email: '',
            password: '',
            passwordConfirm: '',
            username: '',
            first_name: '',
            last_name: '',
            bio: '',
            location: '',
            gender: '',
            currentStep: 1,
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleStep1Submit = this.handleStep1Submit.bind(this);
        this.handleStep2Submit = this.handleStep2Submit.bind(this);
    }

    handleInputChange(event: { target: { name: any; value: any; }; }) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        this.setState({[name]: value} as Pick<RegisterState, keyof RegisterState>);
    }

    handleStep1Submit = (event: { preventDefault: () => void }) => {
        event.preventDefault();
        const {password, passwordConfirm} = this.state;
        if (password.length < 8) {
            alert("Passwords too short!");
            return;
        }
        if (password !== passwordConfirm) {
            alert("Passwords do not match!");
            return;
        }
        this.setState({currentStep: 2});
    }

    handleStep2Submit = (event: { preventDefault: () => void }) => {
        event.preventDefault();
        fetch(`${BACKEND_URL}/register/`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: this.state.username,
                password: this.state.password,
                passwordConfirm: this.state.passwordConfirm,
            })
        })
            .then(res => res.json())
            .then(
                () => {
                    alert("Successfully registered!");
                    this.setState({
                        password: '',
                        passwordConfirm: '',
                        username: '',
                        email: '',
                        first_name: '',
                        last_name: '',
                        bio: '',
                        location: '',
                        gender: '',
                        currentStep: 1,
                    });
                },
                () => {
                    alert('Failed');
                }
            );
    }

    render() {
        const {currentStep} = this.state;

        let stepForm;
        if (currentStep === 1) {
            stepForm = (
                    <Container sx={{display: "flex", flexDirection: "column", justifyContent: "space-between"}}>
                        <h2>Register</h2>
                        <Form onSubmit={this.handleStep1Submit} style={{display: "flex", flexDirection: "column", padding: "8px"}}>
                            <Form.Group controlId="username">
                                <Form.Label>Username</Form.Label>
                                <Form.Control type="text" name="username" value={this.state.username} onChange={this.handleInputChange} required/>
                            </Form.Group>
                            <Form.Group controlId="email">
                                <Form.Label>Email</Form.Label>
                                <Form.Control type="text" name="email" value={this.state.email} onChange={this.handleInputChange} required/>
                            </Form.Group>
                            <Form.Group controlId="password">
                                <Form.Label>Password</Form.Label>
                                <Form.Control type="password" name="password" value={this.state.password}onChange={this.handleInputChange} required/>
                            </Form.Group>
                            <Form.Group controlId="passwordConfirm">
                                <Form.Label>Confirm Password</Form.Label>
                                <Form.Control type="password" name="passwordConfirm" value={this.state.passwordConfirm} onChange={this.handleInputChange} required/>
                            </Form.Group>
                            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', height: '8vh' }}>
                                <Button type="submit" variant="contained"
                                        disabled={!this.state.username || !this.state.password || !this.state.passwordConfirm}>
                                    Continue
                                </Button>
                            </div>
                        </Form>
                    </Container>
            );
        } else {
            stepForm = (
                <Form onSubmit={this.handleStep2Submit}>
                    <Form.Group controlId="first_name">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control type="text" name="first_name" value={this.state.first_name} onChange={this.handleInputChange} required/>
                    </Form.Group>
                    <Form.Group controlId="last_name">
                        <Form.Label>Last Name</Form.Label>
                        <Form.Control type="text" name="last_name" value={this.state.last_name} onChange={this.handleInputChange} required/>
                    </Form.Group>
                    <Form.Group controlId="bio">
                        <Form.Label>Bio</Form.Label>
                        <Form.Control as="textarea" name="bio" value={this.state.bio} onChange={this.handleInputChange}/>
                    </Form.Group>
                    <Form.Group controlId="location">
                        <Form.Label>Location</Form.Label>
                        <Form.Control type="text" name="location" value={this.state.location} onChange={this.handleInputChange} required/>
                    </Form.Group>
                    <Form.Group controlId="gender">
                        <Form.Label>Gender</Form.Label>
                        <Form.Control type="text" name="gender" value={this.state.gender} onChange={this.handleInputChange} required/>
                    </Form.Group>
                    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', height: '8vh' }}>
                        <Button variant="contained" type="submit" disabled={!this.state.username}>
                            Register
                        </Button>
                    </div>
                </Form>
            );
        }

        return (
            <Container style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'space-between', height: '65vh' }}>
                <IconButton component={Link} sx={{ mr: 3 }} to={`/`}>
				    <ArrowBackIcon />
			    </IconButton>
                {stepForm}
            </Container>
        );
    }
}