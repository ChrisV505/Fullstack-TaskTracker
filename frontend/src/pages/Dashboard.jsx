import { Grid, Card, CardContent, Typography, Box, CircularProgress } from '@mui/material';
import { PeopleAlt, Folder, Task, CheckCircle } from '@mui/icons-material';
import { useUsers } from '../hooks/useUsers';
import { useProjects } from '../hooks/useProjects';
import { useTasks } from '../hooks/useTasks';

const StatCard = ({ title, value, icon, color }) => (
  <Card sx={{ height: '100%' }}>
    <CardContent>
      <Box display="flex" alignItems="center" justifyContent="space-between">
        <Box>
          <Typography color="textSecondary" gutterBottom variant="h6">
            {title}
          </Typography>
          <Typography variant="h4" component="h2">
            {value}
          </Typography>
        </Box>
        <Box sx={{ color }}>
          {icon}
        </Box>
      </Box>
    </CardContent>
  </Card>
);

export default function Dashboard() {
  const { data: users, isLoading: usersLoading } = useUsers();
  const { data: projects, isLoading: projectsLoading } = useProjects();
  const { data: tasks, isLoading: tasksLoading } = useTasks();

  if (usersLoading || projectsLoading || tasksLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  const completedTasks = tasks?.filter(task => task.completed).length || 0;

  return (
    <Box>
      <Typography variant="h4" component="h1" gutterBottom>
        Dashboard
      </Typography>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Total Users"
            value={users?.length || 0}
            icon={<PeopleAlt fontSize="large" />}
            color="primary.main"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Total Projects"
            value={projects?.length || 0}
            icon={<Folder fontSize="large" />}
            color="secondary.main"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Total Tasks"
            value={tasks?.length || 0}
            icon={<Task fontSize="large" />}
            color="info.main"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatCard
            title="Completed Tasks"
            value={completedTasks}
            icon={<CheckCircle fontSize="large" />}
            color="success.main"
          />
        </Grid>
      </Grid>
    </Box>
  );
}