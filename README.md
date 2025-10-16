# 🛒 Supermarket Management System

A modern, professional-grade Java Swing GUI application designed for managing complete supermarket operations. The system provides separate portals for administrators to manage inventory and customers to browse products and make purchases with automated billing.

---

## 📋 Project Overview

The **Supermarket Management System** is a comprehensive solution built with Java and MySQL that streamlines both backend inventory management and customer-facing shopping operations. It features a beautiful modern UI with gradient designs, smooth animations, and intuitive navigation.

### 🎯 Purpose
This system enables supermarkets to:
- Maintain real-time product inventory
- Prevent overselling with stock tracking
- Process customer transactions seamlessly
- Generate professional purchase receipts
- Provide a user-friendly shopping experience

---

## ✨ Key Features

### 👨‍💼 Admin Dashboard
- **Inventory Management**: Add, update, and delete products
- **Product Details**: Manage name, price, quantity, and description
- **Price Management**: Update product prices in real-time
- **Stock Control**: Adjust inventory quantities
- **Real-time Tracking**: View total product count and complete inventory
- **Data Persistence**: All changes saved to MySQL database
- **Color-coded Actions**: Easy visual distinction between operations

**Available Actions:**
- ✅ **Add Item** - Insert new products into inventory
- 💰 **Update Price** - Modify product pricing
- 📦 **Update Qty** - Adjust stock quantities
- ❌ **Remove Item** - Delete products with confirmation
- 🔄 **Refresh** - Reload data from database

### 🛍️ Customer Shopping Portal
- **Product Browsing**: View all available items with prices and stock levels
- **Shopping Cart**: Add items with custom quantities
- **Cart Management**: View cart contents, quantities, and total price
- **Checkout Process**: Secure transaction processing
- **Automated Billing**: Generate professional purchase receipts
- **Receipt Printing**: Print bills for customer records
- **Inventory Updates**: Automatic stock reduction after purchase

**Available Operations:**
- ✨ **Browse Products** - View all items in organized table
- 🛒 **Add to Cart** - Select quantity and add items
- 👀 **View Cart** - See all items and total price
- 💳 **Checkout & Pay** - Process purchase and print bill
- 🔄 **Refresh** - Update product availability

### 🎨 Modern User Interface
- **Gradient Headers**: Eye-catching blue-green gradient design
- **Card-Based Layout**: Organized sections with subtle borders and shadows
- **Professional Styling**: Segoe UI fonts with proper hierarchy
- **Hover Effects**: Interactive button feedback
- **Color Coding**: 
  - 🔵 Blue - Primary actions and navigation
  - 🟢 Green - Add/Success operations
  - 🔴 Red - Delete/Critical operations
  - 🟠 Orange - Update/Modify operations
  - 🟣 Purple - Refresh/Secondary actions
- **Alternating Row Colors**: Better table readability
- **Responsive Design**: Adapts to different screen sizes
- **Shadow Effects**: Depth perception and visual hierarchy

### 🔐 Authentication System
- **Role-Based Access**: Separate login portals for Admin and Customer
- **Demo Credentials**: Pre-configured for testing
- **Session Management**: Proper frame disposal and window switching

---

## 🛠️ Technology Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Java (JDK 21+) |
| **GUI Framework** | Swing (AWT) |
| **Database** | MySQL |
| **JDBC Driver** | MySQL Connector/J |
| **IDE Support** | IntelliJ IDEA, Eclipse |

---

## 📦 Project Structure

```
supermarket-management-system/
│
├── src/main/java/app/
│   ├── Main.java                          # Application entry point
│   ├── DBConnection.java                  # MySQL database configuration
│   │
│   ├── dao/
│   │   └── ProductDAO.java                # Database operations (CRUD)
│   │
│   ├── models/
│   │   └── Product.java                   # Product data model
│   │
│   ├── ui/
│   │   ├── LoginFrame.java                # Modern login interface
│   │   ├── AdminFrame.java                # Admin dashboard & controls
│   │   └── CustomerFrame.java             # Customer shopping portal
│   │
│   └── util/
│       └── BillPrinter.java               # Receipt generation & printing
│
├── sql/
│   └── supermarket.sql                    # Database schema & seed data
│
├── SMS.iml                                # IntelliJ project configuration
├── README.md                              # Project documentation
└── lib/
    └── mysql-connector-j-9.4.0.jar        # MySQL JDBC driver
```

---

## 🗄️ Database Schema

### Products Table
```sql
CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  quantity INT NOT NULL DEFAULT 0,
  description VARCHAR(255)
);
```

### Sample Data
| ID | Product | Price | Stock | Description |
|----|---------:|------:|------:|-------------|
| 1 | Milk 1L | Rs 60.20 | 100 | Fresh milk 1 litre |
| 2 | Bread | Rs 50.80 | 200 | Whole wheat bread |
| 3 | Eggs (12) | Rs 6.50 | 150 | Free range eggs - dozen |
| 4 | Apple (1 kg) | Rs 100.00 | 80 | Fresh red apples |

---

## 🚀 Getting Started

### Prerequisites
- **Java Development Kit (JDK)**: Version 21 or higher
- **MySQL Server**: Running on localhost:3306
- **IDE**: IntelliJ IDEA or Eclipse
- **MySQL Connector JAR**: Included in `lib/` folder

### Installation Steps

#### Step 1: Database Setup
```bash
# Start your MySQL server
# Run the SQL script to create database and tables
mysql -u root -p < sql/supermarket.sql
```

#### Step 2: Configure Database Connection
Edit `src/main/java/app/DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/supermarket?useSSL=false&serverTimezone=UTC";
private static final String USER = "root";
private static final String PASS = "your_mysql_password";
```

#### Step 3: Run the Application

**Using IntelliJ IDEA:**
1. Open the project folder
2. IntelliJ will detect it as a Java project
3. Ensure MySQL Connector JAR is in classpath (lib/ folder)
4. Right-click `Main.java` → Run 'Main.main()'

**Using Eclipse:**
1. File → Import → Existing Projects into Workspace
2. Select the project folder
3. Configure Build Path → Add External Jar → mysql-connector-j-9.4.0.jar
4. Right-click `Main.java` → Run As → Java Application

---

## 🔑 Login Credentials

### Admin Access
```
Username: admin
Password: admin123
```
**Access Level**: Full inventory management capabilities

### Customer Access
```
Username: Any input (not validated)
Password: Any input (not validated)
```
**Access Level**: Shopping portal only

---

## 📖 Usage Guide

### 👨‍💼 For Administrators

1. **Launch Application**
   - Run `Main.java` from IDE
   - Click "Admin Login" button

2. **Enter Credentials**
   - Username: `admin`
   - Password: `admin123`

3. **Manage Inventory**
   - **Add Product**: 
     - Fill product details (Name, Price, Quantity, Description)
     - Click "Add Item"
   
   - **Update Price**: 
     - Select product from table
     - Enter new price in price field
     - Click "Update Price"
   
   - **Update Quantity**: 
     - Select product from table
     - Enter new quantity
     - Click "Update Qty"
   
   - **Remove Product**: 
     - Select product from table
     - Click "Remove Item"
     - Confirm deletion

   - **Refresh Data**: 
     - Click "Refresh" to reload from database

### 🛍️ For Customers

1. **Launch Application**
   - Run `Main.java` from IDE
   - Click "Customer" button

2. **Browse Products**
   - View all available items in the table
   - Check prices (in Rs) and available stock

3. **Add Items to Cart**
   - Select a product by clicking on it
   - Enter desired quantity in "Quantity" field
   - Click "Add to Cart"

4. **Manage Shopping Cart**
   - **View Cart**: Click "View Cart" to see items and total
   - **Modify Cart**: Re-add items to change quantities

5. **Checkout & Purchase**
   - Click "Checkout & Pay"
   - System automatically:
     - Validates stock availability
     - Updates database inventory
     - Generates professional receipt
     - Displays bill for printing
   - Click "Print Bill" to generate receipt
   - Cart resets after successful purchase

---

## 📊 Admin Dashboard Features

| Feature | Description | Action |
|---------|------------|--------|
| **Product Table** | Displays ID, Name, Price, Quantity, Description | Click to select |
| **Add Item** | Insert new products with full details | Click "Add Item" |
| **Update Price** | Modify pricing for existing products | Select → Enter Price → "Update Price" |
| **Update Quantity** | Adjust stock levels | Select → Enter Qty → "Update Qty" |
| **Remove Item** | Delete products with confirmation | Select → "Remove Item" → Confirm |
| **Total Count** | Displays total products in system | Auto-updated |
| **Refresh** | Reload all data from database | Click "Refresh" |

---

## 🛍️ Customer Portal Features

| Feature | Description | Usage |
|---------|------------|-------|
| **Product Display** | Browse all items with prices & stock | View in table |
| **Stock Information** | Real-time inventory availability | Shows in "Stock Available" column |
| **Quantity Input** | Specify purchase amount | Enter number in quantity field |
| **Add to Cart** | Select item + quantity | Click "Add to Cart" |
| **View Cart** | See items, quantities, total | Click "View Cart" |
| **Checkout** | Process purchase transaction | Click "Checkout & Pay" |
| **Receipt** | Professional bill generation | Auto-generated after checkout |
| **Print Bill** | Generate physical receipt | Click "Print Bill" button |

---

## 🎨 Design Features

### UI Components

**Login Frame:**
- Gradient blue background (15, 76, 129 → 25, 118, 210)
- White card with shadow effect
- Rounded input fields with subtle borders
- Left-aligned labels for clarity
- Smooth button hover effects

**Admin Frame:**
- Blue gradient header
- Organized product table with center alignment
- Right-side control panel with forms
- Color-coded action buttons
- Real-time total product counter

**Customer Frame:**
- Teal-blue gradient header with shopping icon
- Alternating row colors in product table
- Right-side card-based layout
- Expandable cart management section
- Action buttons with clear visual hierarchy

**Color Palette:**
```
Primary Blue:    #0AA9F4 (3, 169, 244)
Dark Blue:       #0277BD (2, 119, 189)
Light Blue:      #1976D2 (25, 118, 210)
Success Green:   #4CAF50 (76, 175, 80)
Danger Red:      #F44336 (244, 67, 54)
Warning Orange:  #FF9800 (255, 152, 0)
Secondary Purple: #9C27B0 (156, 39, 176)
```

---

## 🔄 Data Flow

```
User Login
    ↓
├─→ Admin Selected → AdminFrame → ProductDAO → MySQL Database
│                      ↓
│                  [Add/Update/Delete Products]
│
└─→ Customer Selected → CustomerFrame → ProductDAO → MySQL Database
                           ↓
                       [Browse/Add to Cart]
                           ↓
                       [Checkout]
                           ↓
                       [Update Inventory]
                           ↓
                       [Generate Bill]
```

---

## 🐛 Troubleshooting

### Database Connection Error
**Problem**: "Communications link failure" or connection timeout
- ✅ Verify MySQL server is running
- ✅ Check credentials in `DBConnection.java`
- ✅ Ensure database `supermarket` exists
- ✅ Verify JDBC driver is in classpath

### No Products Displaying
**Problem**: Table is empty after login
- ✅ Run `sql/supermarket.sql` to populate seed data
- ✅ Check database connection status
- ✅ Verify `products` table structure exists

### Build Errors
**Problem**: Compilation errors or missing classes
- ✅ Ensure JDK 21+ is installed
- ✅ Configure MySQL Connector JAR in Build Path
- ✅ Rebuild project (Clean + Build)

### GUI Rendering Issues
**Problem**: Buttons not showing colors or layouts misaligned
- ✅ Ensure system has proper display scaling
- ✅ Check Java/Swing version compatibility
- ✅ Rebuild and restart application

---

## 📝 Code Structure

### Main.java
Entry point that initializes the Swing UI Manager and displays LoginFrame

### DBConnection.java
Singleton pattern for MySQL database connectivity
- Manages JDBC connections
- Configures URL, username, password
- Handles connection lifecycle

### ProductDAO.java
Data Access Object for database operations:
- `getAllProducts()` - Fetch all products
- `getProductById()` - Fetch specific product
- `addProduct()` - Insert new product
- `updateProduct()` - Update product details
- `updatePrice()` - Update only price
- `deleteProduct()` - Remove product

### Product.java
Model class representing product entity with properties:
- id, name, price, quantity, description

### UI Frames
- **LoginFrame**: Authentication interface
- **AdminFrame**: Inventory management dashboard
- **CustomerFrame**: Shopping portal

### BillPrinter.java
Utility for receipt generation and printing with custom JFrame display

---

## ✅ Features Implemented

- ✅ Modern gradient-based UI design
- ✅ Role-based login system (Admin/Customer)
- ✅ Real-time inventory management
- ✅ Shopping cart functionality
- ✅ Automated billing system
- ✅ Receipt printing capability
- ✅ MySQL database integration
- ✅ CRUD operations for products
- ✅ Input validation
- ✅ Error handling
- ✅ Responsive UI components
- ✅ Stock tracking and validation

---

## 📈 Future Enhancements

- 🔒 User authentication with password hashing
- 👥 Multiple admin accounts with different roles
- 🏷️ Product categories and filtering
- 🔍 Search functionality
- 📊 Sales analytics and reports
- 💾 Order history and customer records
- 💳 Payment gateway integration
- 🌍 Multi-language support
- 📱 Mobile application version
- 📦 Barcode scanning support
- 💬 Inventory alerts and notifications

---

## 📄 License

This project is open-source and available for educational, commercial, and personal use.

---

## 👥 Support & Contact

For issues, bugs, feature requests, or general inquiries:
- Review the troubleshooting section above
- Check database configuration in `DBConnection.java`
- Ensure all prerequisites are met
- Verify SQL script has been executed

---

**Version**: 1.0.0  
**Status**: Active & Maintained  
**Last Updated**: 2025  
**Compatible With**: Java 21+, MySQL 5.7+, Windows/Linux/Mac
