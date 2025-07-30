import { useState } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Button,
  Typography,
  Box,
  CircularProgress,
  Chip,
} from '@mui/material';
import { Edit, Delete, Add, Visibility } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { useProjects, useDeleteProject } from '../../hooks/useProjects';
import ProjectForm from '../../components/projects/ProjectForm';

export default function ProjectsPage() {
  const [openForm, setOpenForm] = useState(false);
  const [selectedProject, setSelectedProject] = useState(null);
  const navigate = useNavigate();
  
  const { data: projects, isLoading, error } = useProjects();
  const deleteProject = useDeleteProject();

  const handleEdit = (project) => {
    setSelectedProject(project);
    setOpenForm(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this project?')) {
      try {
        await deleteProject.mutateAsync(id);
      } catch (error) {
        console.error('Error deleting project:', error);
      }
    }
  };

  const handleView = (projectId) => {
    navigate(`/projects/${projectId}`);
  };

  const handleCloseForm = () => {
    setOpenForm(false);
    setSelectedProject(null);
  };

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box>
        <Typography color="error">Error loading projects: {error.message}</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1">
          Projects
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          onClick={() => setOpenForm(true)}
        >
          Add Project
        </Button>
      </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Assigned User</TableCell>
              <TableCell align="right">Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {projects?.map((project) => (
              <TableRow key={project.id}>
                <TableCell>{project.id}</TableCell>
                <TableCell>{project.name}</TableCell>
                <TableCell>
                  {project.user ? (
                    <Chip 
                      label={`${project.user.name} (${project.user.email})`}
                      size="small"
                    />
                  ) : (
                    <Typography variant="body2" color="textSecondary">
                      No user assigned
                    </Typography>
                  )}
                </TableCell>
                <TableCell align="right">
                  <IconButton
                    color="info"
                    onClick={() => handleView(project.id)}
                  >
                    <Visibility />
                  </IconButton>
                  <IconButton
                    color="primary"
                    onClick={() => handleEdit(project)}
                  >
                    <Edit />
                  </IconButton>
                  <IconButton
                    color="error"
                    onClick={() => handleDelete(project.id)}
                  >
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <ProjectForm
        open={openForm}
        onClose={handleCloseForm}
        project={selectedProject}
      />
    </Box>
  );
}