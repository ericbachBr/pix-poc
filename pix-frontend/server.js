const express = require("express");
const bodyParser = require("body-parser");
const path = require("path");

const app = express();
const PORT = 3000;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, "public")));
app.set("view engine", "ejs");

const agendamentoRoutes = require("./routes/agendamento");
app.use("/", agendamentoRoutes);

app.listen(PORT, () => {
  console.log(`Frontend rodando em http://localhost:${PORT}`);
});
