# Home Appliance Manager Backend

A Spring Boot application for managing home appliances, including product details, warranties, invoices, and vendor information. The application provides RESTful APIs with JWT authentication and integrates with Cloudinary for image storage.

## Features

- **User Authentication & Authorization**: JWT-based authentication with Spring Security
- **Product Management**: CRUD operations for home appliances
- **Invoice Management**: Store and manage invoice details with image uploads
- **Warranty Tracking**: Automatic warranty expiry checking with scheduled tasks
- **Vendor Information**: Store vendor details for each product
- **Image Storage**: Invoice images stored securely in Cloudinary
- **Product Sharing**: Public endpoints for sharing product information

## Tech Stack

- **Framework**: Spring Boot 3.5.6
- **Language**: Java 21
- **Database**: MySQL 8
- **Security**: Spring Security with JWT (JSON Web Tokens)
- **Image Storage**: Cloudinary
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Containerization**: Docker & Docker Compose

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker and Docker Compose
- Cloudinary account (for image storage)

## Project Structure

```
src/main/java/com/pranjal/
├── config/              # Configuration classes (Security, JWT, Cloudinary)
├── controller/          # REST API controllers
├── dtos/               # Data Transfer Objects
├── entity/             # JPA entities
│   └── embeddable/     # Embeddable entities
├── jwt/                # JWT utility classes
├── repository/         # JPA repositories
└── service/            # Business logic services
    └── Implementation/ # Service implementations
```

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd home-appliance-manager-backend
```

### 2. Set Up Environment Variables

Create a `.env` file in the project root or set the following environment variables:

```properties
# Database Configuration
DATABASE_URL=jdbc:mysql://localhost:3306/appliance_manager
DATABASE_USERNAME=myuser
DATABASE_PASSWORD=myuserpass

# Cloudinary Configuration
CLOUDINARY_NAME=your_cloudinary_cloud_name
CLOUDINARY_API_KEY=your_cloudinary_api_key
CLOUDINARY_API_SECRET=your_cloudinary_api_secret

# JWT Configuration
JWT_SECRET_KEY=your_secret_key_min_256_bits
```

### 3. Start MySQL Database

Using Docker Compose:

```bash
docker-compose up -d
```

This will start a MySQL container with the following default credentials:
- Host: localhost
- Port: 3306
- Database: appliance_manager
- Username: myuser
- Password: myuserpass

### 4. Build the Application

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

Once the application is running, access the Swagger UI documentation at:

```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

### Authentication

- `POST /api/v1/auth/register` - Register a new user
- `POST /api/v1/auth/login` - Login and receive JWT token

### Products (Requires Authentication)

- `POST /api/v1/products` - Add a new product (multipart/form-data)
- `GET /api/v1/products/users?userId={userId}` - Get all products for a user
- `GET /api/v1/products?productId={productId}` - Get product by ID
- `PUT /api/v1/products` - Update product details
- `DELETE /api/v1/products?productId={productId}` - Delete a product

### Public Endpoints

- `GET /api/v1/products/share?productId={productId}` - Share product details (no authentication required)

## Request/Response Examples

### Register User

```json
POST /api/v1/auth/register
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "mobileNo": "1234567890"
}
```

### Login

```json
POST /api/v1/auth/login
{
  "email": "john@example.com",
  "password": "securePassword123"
}

Response:
{
  "userId": "uuid-string",
  "fullname": "John Doe",
  "email": "john@example.com",
  "token": "jwt-token"
}
```

### Add Product

```
POST /api/v1/products
Content-Type: multipart/form-data

Parts:
- request (application/json):
{
  "userId": "user-uuid",
  "productDetail": {
    "name": "Refrigerator",
    "model_no": "RF123",
    "category": "Kitchen Appliance",
    "description": "Double door refrigerator"
  },
  "vendorDetail": {
    "name": "ABC Electronics",
    "contactNo": "9876543210",
    "address": "123 Main St"
  },
  "warrantyDetail": {
    "warranty": 24,
    "warrantyExpiry": "2026-10-01",
    "isWarrantyExpired": false
  },
  "invoiceRequest": {
    "invoiceNo": "INV-001",
    "totalAmount": 25000.00,
    "paymentMethod": "UPI",
    "Date": "2024-10-01"
  }
}
- invoiceImage (file): [image file]
```

## Database Schema

### User Table
- id (Primary Key)
- user_id (Unique UUID)
- password (Encrypted)
- full_name
- email
- mobile_no

### Product Table
- id (Primary Key)
- product_id (Unique UUID)
- product_name, model_no, category, description
- vendor_name, contact_no, address
- warranty, warranty_expiry, is_warranty_expired
- invoice_no, total_amount, payment_method, date
- invoice_image, public_id
- user_id (Foreign Key)

## Scheduled Tasks

The application includes a scheduled task that runs daily at midnight to check and update warranty expiry status for all products.

## Security

- All endpoints (except authentication and share endpoints) require JWT authentication
- Passwords are encrypted using BCrypt
- CORS is configured to allow requests from `http://localhost:5173` (frontend)
- JWT tokens expire after 1 hour

## CORS Configuration

The application is configured to accept requests from:
- Origin: `http://localhost:5173`
- Methods: GET, POST, PUT, DELETE, OPTIONS
- Credentials: Allowed

Update the CORS configuration in `SecurityConfig.java` for production deployments.

## Payment Methods

Supported payment methods (enum):
- CASH
- UPI
- ONLINE_BANKING
- CARD

## Error Handling

The application returns appropriate HTTP status codes:
- 200: Success
- 201: Created
- 204: No Content
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

## Development

### Running Tests

```bash
mvn test
```

### Building for Production

```bash
mvn clean package
```

The executable JAR will be created in the `target/` directory.

## Docker Deployment

Build and run the application with Docker:

```bash
# Build the image
docker build -t appliance-manager-backend .

# Run the container
docker run -p 8080:8080 \
  -e DATABASE_URL=jdbc:mysql://host.docker.internal:3306/appliance_manager \
  -e DATABASE_USERNAME=myuser \
  -e DATABASE_PASSWORD=myuserpass \
  -e CLOUDINARY_NAME=your_cloud_name \
  -e CLOUDINARY_API_KEY=your_api_key \
  -e CLOUDINARY_API_SECRET=your_api_secret \
  -e JWT_SECRET_KEY=your_secret_key \
  appliance-manager-backend
```

## Troubleshooting

### Database Connection Issues
- Ensure MySQL container is running: `docker-compose ps`
- Check database credentials in environment variables
- Verify port 3306 is not in use by another service

### JWT Token Issues
- Ensure JWT_SECRET_KEY is at least 256 bits (32 characters)
- Check token expiration time (default: 1 hour)

### Image Upload Issues
- Verify Cloudinary credentials are correct
- Check network connectivity to Cloudinary services
- Ensure file size is within limits

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the terms specified in the project.

## Contact

For questions or support, please contact the development team.

## Acknowledgments

- Spring Boot team for the excellent framework
- Cloudinary for image storage services
- All contributors to this project
