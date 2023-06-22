const app = require("express")();
const server = require("http").createServer(app);
const cors = require("cors");

app.use(cors());

const PORT = process.env.PORT || 5000;

app.get("/", (req, res) => {
  res.send("Running");
});


server.listen(PORT, () => console.log(`Server is running on port ${PORT}`));
