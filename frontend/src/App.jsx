import { useEffect, useState } from 'react'
import axios from 'axios';
import './App.css';

function App() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/users')
    .then(response => {
      setUsers(response.data);
    })
    .catch(error => {
      console.error('Error fetching users:', error);
    })
  }, []);

  return (
    <div>
      <h1>Task Tracker</h1>
      <p>Starting to build for taskTracker</p>

      <table border="1" cellPadding="8" cellSpacing="0">
        <thead>
          <tr>
            <th>ID</th>
            <th>Email</th>
          </tr>
        </thead>

        <tbody>
          {users.map(user => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.email}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default App
