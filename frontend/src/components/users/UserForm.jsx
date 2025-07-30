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
} from '@mui/material';
import { useCreateUser, useUpdateUser } from '../../hooks/useUsers';

const userValidationSchema = Yup.object({
  name: Yup.string().required('Name is required'),
  email: Yup.string().email('Invalid email').required('Email is required'),
});

export default function UserForm({ open, onClose, user = null }) {
  const createUser = useCreateUser();
  const updateUser = useUpdateUser();
  const isEditing = !!user;

  const initialValues = {
    name: user?.name || '',
    email: user?.email || '',
  };

  const handleSubmit = async (values, { setSubmitting }) => {
    try {
      if (isEditing) {
        await updateUser.mutateAsync({ id: user.id, data: values });
      } else {
        await createUser.mutateAsync(values);
      }
      onClose();
    } catch (error) {
      console.error('Error submitting user:', error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
      <DialogTitle>{isEditing ? 'Edit User' : 'Create User'}</DialogTitle>
      <Formik
        initialValues={initialValues}
        validationSchema={userValidationSchema}
        onSubmit={handleSubmit}
        enableReinitialize
      >
        {({ errors, touched, isSubmitting }) => (
          <Form>
            <DialogContent>
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <Field
                  as={TextField}
                  name="name"
                  label="Name"
                  fullWidth
                  error={touched.name && !!errors.name}
                  helperText={touched.name && errors.name}
                />
                <Field
                  as={TextField}
                  name="email"
                  label="Email"
                  type="email"
                  fullWidth
                  error={touched.email && !!errors.email}
                  helperText={touched.email && errors.email}
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
  );
}