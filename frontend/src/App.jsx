import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/tasks")
    .then(res => res.json())
    .then(data => setTasks(data))
  }, []);

  return (
      <div>
        <h1>Task Tracker</h1>
        <p>Starting to build frontend for tasktracker</p>
        <ul>
          {tasks.map(task => (
            <li key={task.id}>{task.title}</li>
          ))}
        </ul>
      </div>
  );
}

export default App