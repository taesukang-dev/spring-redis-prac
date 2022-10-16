import {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const RoomList = () => {
    const [room, setRoom] = useState([])
    const [roomName, setRoomName] = useState("")
    const navigate = useNavigate()

    useEffect(() => {
        findAllRoom()
    },[])

    const findAllRoom = () => axios.get("http://localhost:8080/chat/rooms")
        .then(res => {
            console.log(res)
            setRoom([...res.data])
        })

    const createRoom = () => {
        axios.post(`http://localhost:8080/chat/room?name=${roomName}`)
            .then(res => findAllRoom())
    }

    return (
     <>
         <h2>room</h2>
         <input onChange={(e) => setRoomName(e.target.value)} />
         <button onClick={createRoom}>방 만들기</button>
         {
             room.map((e, i) =>
                 <div key={i} onClick={() => navigate(`/${e.roomId}`)}>
                     <div>{e.roomId}</div>
                     <div>{e.name}</div>
                 </div>
             )
         }
     </>
    )
}

export default RoomList;