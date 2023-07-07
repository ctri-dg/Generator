FROM node:20-alpine as builder
EXPOSE 5173
WORKDIR /app
COPY package.json .
RUN yarn install
COPY . .
CMD ["yarn", "dev"]