import { useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import "./App.css";

function App() {

  // ================= STATES =================
const navigate = useNavigate();
  const [tasks, setTasks] = useState([]);

  const [title, setTitle] = useState("");

  const [status, setStatus] = useState("Pending");
  const [searchTerm, setSearchTerm] = useState("");

  const [editingId, setEditingId] = useState(null);

  const token = localStorage.getItem("token");
  console.log("TOKEN FROM LOCAL STORAGE =", token);

  // ================= GET TASKS =================

  const getTasks = async () => {

  try {

    const response = await fetch(
      "http://localhost:8080/tasks",
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );

    const data = await response.json();

    console.log("API Response:", data);

    if (Array.isArray(data)) {

      setTasks(data);

    } else {

      console.error("Expected array but got:", data);

      setTasks([]);
    }

  } catch (error) {

    console.log(error);

    setTasks([]);
  }
};
  // ================= LOAD TASKS =================

  useEffect(() => {

  const token = localStorage.getItem("token");

  console.log("TOKEN CHECK =", token);

  if (!token) {
    console.log("REDIRECTING TO LOGIN");
    navigate("/login");
    return;
  }

  getTasks();

}, [navigate]);
// ================= ADD + UPDATE TASK =================

  const addTask = async () => {

    if(title === "") {

      alert("Please enter task");

      return;
    }

    const task = {

      title: title,

      description: "Frontend JWT Task",

      priority: "High",

      status: status
    };

    try {

      // UPDATE TASK
      if(editingId) {

        await fetch(
          `http://localhost:8080/tasks/${editingId}`,
          {
            method: "PUT",

            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`
            },

            body: JSON.stringify(task)
          }
        );

        setEditingId(null);

      } else {
  
          // ADD TASK
        await fetch(
          "http://localhost:8080/tasks",
          {
            method: "POST",

            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`
            },

            body: JSON.stringify(task)
          }
        );
      }

      setTitle("");

      setStatus("Pending");

      getTasks();

    } catch(error) {

      console.log(error);
    }
  };

  // ================= DELETE TASK =================

  const deleteTask = async (id) => {

    try {

      await fetch(
        `http://localhost:8080/tasks/${id}`,
        {
          method: "DELETE",

          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      getTasks();

    } catch(error) {

      console.log(error);
    }
  };

  // ================= LOGOUT =================

 const logout = () => {

  localStorage.removeItem("token");

  window.location.href = "/login";
};
  // ================= STATUS COLOR =================

  const getStatusColor = (taskStatus) => {

    if(taskStatus === "Pending") {

      return "red";
    }

    if(taskStatus === "In Progress") {

      return "orange";
    }

    if(taskStatus === "Completed") {

      return "green";
    }

    return "black";
  };

  // ================= UI =================
const filteredTasks = tasks.filter((task) =>
  task.title.toLowerCase().includes(searchTerm.toLowerCase())
);
  return (

    <div className="container">

      <div className="task-box">

        <h1>Smart Task Manager</h1>

        {/* LOGOUT BUTTON */}

        <button
          className="logout-btn"
          onClick={logout}
        >
          Logout
        </button>

        {/* INPUT SECTION */}

        <div className="task-input">

          {/* TASK INPUT */}

          <input
            type="text"
            placeholder="Enter Task"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />

          {/* STATUS DROPDOWN */}

          <select
            value={status}
            onChange={(e) => setStatus(e.target.value)}
          >

            <option value="Pending">
              Pending
            </option>

            <option value="In Progress">
              In Progress
            </option>

            <option value="Completed">
              Completed
            </option>

          </select>

          {/* ADD BUTTON */}

          <button
            className="add-btn"
            onClick={addTask}
          >
            {editingId ? "Update" : "Add"}
          </button>

        </div>
        <input
  type="text"
  placeholder="Search Task..."
  value={searchTerm}
  onChange={(e) => setSearchTerm(e.target.value)}
  style={{
    width: "100%",
    padding: "10px",
    marginBottom: "15px"
  }}
/>

        {/* TASK LIST */}

        {

         Array.isArray(filteredTasks) &&
filteredTasks.map((task) => (
            <div
              className="task-item"
              key={task.id}
            >

              <span>

                {task.title}

                {" - "}

                <span
                  style={{
                    color: getStatusColor(task.status),
                    fontWeight: "bold"
                  }}
                >
                  {task.status}
                </span>

              </span>

              <div>

                {/* UPDATE BUTTON */}

                <button
                  className="update-btn"
                  onClick={() => {

                    setTitle(task.title);

                    setStatus(task.status);

                    setEditingId(task.id);
                  }}
                >
                  Update
                </button>

                {/* DELETE BUTTON */}

                <button
                  className="delete-btn"
                  onClick={() => deleteTask(task.id)}
                >
                  Delete
                </button>

              </div>

            </div>
          ))
        }

      </div>

    </div>
  );
}

export default App;