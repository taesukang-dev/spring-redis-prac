import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import axios from "axios";

const RoomDetail = () => {
    let reconnect = 0
    const id = useParams()
    let [chatList, setChatList] = useState([])
    const [chatData, setChatData] = useState('')

    let sock = new SockJS("http://localhost:8080/ws-stomp");
    let ws = Stomp.over(sock)

    const sendMessage = () => {
        ws.send("/pub/chat/message", {}, JSON.stringify({type: 'TALK', roomId: id.id, sender: 'tkang', message: chatData}))
    }

    const connect = () => {
        ws.connect({}, (frame) => {
            ws.subscribe(`/sub/chat/room/${id.id}`, (message) => {
                const received = JSON.parse(message.body)
                setChatList((_chatList) => [..._chatList, received])
            })
            ws.send("/pub/chat/message", {}, JSON.stringify({type: 'ENTER', roomId: id.id, sender: 'tkang'}))
        }, (error) => {
            if (reconnect++ <= 5) {
                setTimeout(() => {
                    console.log("connecting...")
                    sock = new SockJS("http://localhost:8080/ws-stomp")
                    ws = Stomp.over(ws)
                    connect()
                }, 10 * 1000)
            }
        })
    }

    useEffect(() => {
        axios.get(`http://localhost:8080/chat/room/${id.id}`)
            .then(res => console.log(res))
        connect()
    }, [])

    return (
        <>
            <div>
                {
                    chatList.map((e) => <div>{e.message}</div>)
                }
                hi!
                <input onChange={(e) => setChatData(e.target.value)}/>
                <button onClick={() => sendMessage()}>전송</button>
            </div>
        </>
    )
}

export default RoomDetail