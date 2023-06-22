# the docker build command should be invoked from outside the dune-api directory, e.g.:
# docker build -t tresata-dune-api:latest -f dune-api/docker/Dockerfile .

FROM node:14.20.0
RUN mkdir /opt/tresata-dune-api
WORKDIR /opt/tresata-dune-api
COPY dune-api .
COPY --chown=node:node . .
USER node
CMD ["node", "index.js"]
