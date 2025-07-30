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
  CircularProgress,
} from '@mui/material';
import { useCreateProject, useUpdateProject } from '../../hooks/useProjects';
import { useUsers } from '../../hooks/useUsers';

const projectValidationSchema = Yup.object({
  name: Yup.string().required('Name is required'),
  user: Yup.object().required('User is required'),
});

export default function ProjectForm({ open, onClose, project = null }) {
  const createProject = useCreateProject();
  const updateProject = useUpdateProject();
  const { data: users, isLoading: usersLoading } = useUsers();
  const isEditing = !!project;

  const initialValues = {
    name: project?.name || '',
    user: project?.user || null,
  };

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      if (isEditing) {
        await updateProject.mutateAsync({ id: project.id, data: values });
      } else {
        await createProject.mutateAsync(values);
      }
      onClose();
    } catch (error) {
      console.error('Error submitting project:', error);
    } finally {
      setSubmitting(false);
    }
  };

  if (usersLoading) {
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
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>{isEditing ? 'Edit Project' : 'Create Project'}</DialogTitle>
      <Formik
        initialValues={initialValues}
        validationSchema={projectValidationSchema}
        onSubmit={handleSubmit}
        enableReinitialize
      >
        {({ errors, touched, isSubmitting, values, setFieldValue }) => (
          <Form>
            <DialogContent>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <Field
                  as={TextField}
                  name="name"
                  label="Project Name"
                  fullWidth
                  error={touched.name && !!errors.name}
                  helperText={touched.name && errors.name}
                />
                <TextField
                  select
                  label="Assign to User"
                  value={values.user?.id || ''}
                  onChange={(e) => {
                    const selectedUser = users?.find(user => user.id === e.target.value);
                    setFieldValue('user', selectedUser || null);
                  }}
                  fullWidth
                  error={touched.user && !!errors.user}
                  helperText={touched.user && errors.user}
                >
                  {users?.map((user) => (
                    <MenuItem key={user.id} value={user.id}>
                      {user.name} ({user.email})
                    </MenuItem>
                  ))}
                </TextField>
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
  );
}