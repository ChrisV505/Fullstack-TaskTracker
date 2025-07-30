# TaskTracker Frontend

A modern React frontend for the TaskTracker application built with Vite, Material UI, and React Query.

## Features

- **Dashboard**: Overview with statistics for users, projects, and tasks
- **User Management**: Complete CRUD operations for users
- **Project Management**: Create, edit, delete, and view projects with user assignments
- **Task Management**: Full task lifecycle management with filtering and sorting
- **Material UI Design**: Modern, responsive UI components
- **React Router**: Client-side routing for smooth navigation
- **React Query**: Efficient data fetching with caching and error handling
- **Formik Forms**: Robust form handling with validation
- **Error Handling**: Graceful error messages and network error handling
- **Responsive Design**: Works on desktop, tablet, and mobile devices

## Tech Stack

- **React 19** - UI framework
- **Vite** - Build tool and dev server
- **Material UI (MUI)** - Component library and design system
- **React Router** - Client-side routing
- **React Query (TanStack Query)** - Data fetching and state management
- **Formik + Yup** - Form handling and validation
- **Axios** - HTTP client
- **Day.js** - Date handling
- **React Hot Toast** - Notifications

## Project Structure

```
src/
├── components/           # Reusable UI components
│   ├── layout/          # Layout components (Navigation, etc.)
│   ├── users/           # User-specific components
│   ├── projects/        # Project-specific components
│   ├── tasks/           # Task-specific components
│   └── common/          # Shared components
├── pages/               # Page components
│   ├── users/           # User management pages
│   ├── projects/        # Project management pages
│   └── tasks/           # Task management pages
├── hooks/               # Custom React hooks for API calls
├── services/            # API service layer
├── types/               # Type definitions
└── utils/               # Utility functions
```

## API Integration

The frontend connects to the Spring Boot backend running on `http://localhost:8080` with the following endpoints:

- **Users**: `/api/users` - User CRUD operations
- **Projects**: `/api/projects` - Project CRUD operations  
- **Tasks**: `/api/tasks` - Task CRUD operations with filtering

## Key Components

### Layout
- Responsive sidebar navigation with Material UI drawer
- App bar with menu toggle for mobile
- Consistent layout across all pages

### Forms
- User form with name and email validation
- Project form with user assignment dropdown
- Task form with date picker, priority selection, and project assignment
- All forms use Formik for validation and submission

### Data Management
- React Query for efficient API calls and caching
- Automatic refetching on mutations
- Error handling with toast notifications
- Loading states with Material UI progress indicators

### Task Features
- Priority filtering (High, Medium, Low)
- Status filtering (Completed, Pending)
- Inline status toggle
- Due date display
- Project assignment visualization

## Development

### Prerequisites
- Node.js 18+
- npm or yarn
- Backend API running on localhost:8080

### Setup
```bash
npm install
npm run dev
```

### Build
```bash
npm run build
```

### Lint
```bash
npm run lint
```

## Error Handling

The application gracefully handles:
- Network connection errors
- API validation errors
- Loading states
- Empty data states
- User-friendly error messages

## Responsive Design

- Mobile-first approach
- Collapsible sidebar navigation on mobile
- Responsive tables and cards
- Touch-friendly interface elements

## Future Enhancements

- Real-time updates with WebSocket
- Advanced filtering and search
- Bulk operations
- Data export functionality
- Dark mode theme
- User authentication and authorization
