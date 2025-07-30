import { Formik, Form, Field } from 'formik';
import * as Yup from 'yup';
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  Box,
  MenuItem,
  FormControlLabel,
  Checkbox,
  CircularProgress,
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { useCreateTask, useUpdateTask } from '../../hooks/useTasks';
import { useProjects } from '../../hooks/useProjects';
import { Priority } from '../../types';

const taskValidationSchema = Yup.object({
  title: Yup.string().required('Title is required'),
  description: Yup.string(),
  dueDate: Yup.date(),
  priority: Yup.string().required('Priority is required'),
  completed: Yup.boolean(),
  project: Yup.object().nullable(),
});

export default function TaskForm({ open, onClose, task = null }) {
  const createTask = useCreateTask();
  const updateTask = useUpdateTask();
  const { data: projects, isLoading: projectsLoading } = useProjects();
  const isEditing = !!task;

  const initialValues = {
    title: task?.title || '',
    description: task?.description || '',
    dueDate: task?.dueDate ? dayjs(task.dueDate) : null,
    priority: task?.priority || Priority.MEDIUM,
    completed: task?.completed || false,
    project: task?.project || null,
  };

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      const formattedValues = {
        ...values,
        dueDate: values.dueDate ? values.dueDate.format('YYYY-MM-DD') : null,
      };

      if (isEditing) {
        await updateTask.mutateAsync({ id: task.id, data: formattedValues });
      } else {
        await createTask.mutateAsync(formattedValues);
      }
      onClose();
    } catch (error) {
      console.error('Error submitting task:', error);
    } finally {
      setSubmitting(false);
    }
  };

  if (projectsLoading) {
    return (
      <Dialog open={open} onClose={onClose}>
        <DialogContent>
          <Box display="flex" justifyContent="center" p={3}>
            <CircularProgress />
          </Box>
        </DialogContent>
      </Dialog>
    );
  }

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
        <DialogTitle>{isEditing ? 'Edit Task' : 'Create Task'}</DialogTitle>
        <Formik
          initialValues={initialValues}
          validationSchema={taskValidationSchema}
          onSubmit={handleSubmit}
          enableReinitialize
        >
          {({ errors, touched, isSubmitting, values, setFieldValue }) => (
            <Form>
              <DialogContent>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                  <Field
                    as={TextField}
                    name="title"
                    label="Task Title"
                    fullWidth
                    error={touched.title && !!errors.title}
                    helperText={touched.title && errors.title}
                  />
                  <Field
                    as={TextField}
                    name="description"
                    label="Description"
                    multiline
                    rows={3}
                    fullWidth
                    error={touched.description && !!errors.description}
                    helperText={touched.description && errors.description}
                  />
                  <DatePicker
                    label="Due Date"
                    value={values.dueDate}
                    onChange={(date) => setFieldValue('dueDate', date)}
                    renderInput={(params) => <TextField {...params} fullWidth />}
                  />
                  <TextField
                    select
                    label="Priority"
                    value={values.priority}
                    onChange={(e) => setFieldValue('priority', e.target.value)}
                    fullWidth
                  >
                    {Object.values(Priority).map((priority) => (
                      <MenuItem key={priority} value={priority}>
                        {priority}
                      </MenuItem>
                    ))}
                  </TextField>
                  <TextField
                    select
                    label="Project"
                    value={values.project?.id || ''}
                    onChange={(e) => {
                      const selectedProject = projects?.find(project => project.id === e.target.value);
                      setFieldValue('project', selectedProject || null);
                    }}
                    fullWidth
                  >
                    <MenuItem value="">No Project</MenuItem>
                    {projects?.map((project) => (
                      <MenuItem key={project.id} value={project.id}>
                        {project.name}
                      </MenuItem>
                    ))}
                  </TextField>
                  <FormControlLabel
                    control={
                      <Checkbox
                        checked={values.completed}
                        onChange={(e) => setFieldValue('completed', e.target.checked)}
                      />
                    }
                    label="Completed"
                  />
                </Box>
              </DialogContent>
              <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button
                  type="submit"
                  variant="contained"
                  disabled={isSubmitting}
                >
                  {isEditing ? 'Update' : 'Create'}
                </Button>
              </DialogActions>
            </Form>
          )}
        </Formik>
      </Dialog>
    </LocalizationProvider>
  );
}