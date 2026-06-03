import React, { useState } from "react";

function Register() {

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const registerUser = async () => {

    try {

      await fetch(
        "http://localhost:8080/auth/register",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            name,
            email,
            password
          })
        }
      );

      alert("Registration Successful");

    } catch (error) {

      console.log(error);

      alert("Registration Failed");
    }
  };

  return (
    <div style={{ padding: "30px" }}>

      <h2>Register</h2>

      <input
        placeholder="Name"
        value={name}
        onChange={(e) =>
          setName(e.target.value)
        }
      />

      <br /><br />

      <input
        placeholder="Email"
        value={email}
        onChange={(e) =>
          setEmail(e.target.value)
        }
      />

      <br /><br />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) =>
          setPassword(e.target.value)
        }
      />

      <br /><br />

      <button onClick={registerUser}>
        Register
      </button>

    </div>
  );
}

export default Register;