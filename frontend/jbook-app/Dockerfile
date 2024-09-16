# Dockerfile for Next.js with TypeScript
FROM node:18-alpine

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy the project files
COPY . .

# Build the Next.js app, which ignores TypeScript errors
RUN npm run build

# Expose the app port
EXPOSE 8005

# Run the app
CMD ["npm", "run", "start"]
