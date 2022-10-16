import './App.css';
import {Route, Routes} from "react-router-dom";
import RoomList from "./pages/RoomList";
import RoomDetail from "./pages/RoomDetail";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path={"/"} element={<RoomList />}/>
        <Route path={"/:id"} element={<RoomDetail />}/>
      </Routes>
    </div>
  );
}

export default App;
