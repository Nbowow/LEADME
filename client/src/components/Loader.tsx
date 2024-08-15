import { useRecoilState } from "recoil";
import { IsEnteredAtom } from "stores/index";
import { Html, useProgress } from "@react-three/drei";
import styled, { keyframes } from "styled-components";
import { useEffect } from "react";

interface LoaderProps {
  isCompleted?: boolean;
}

export const Loader: React.FC<LoaderProps> = ({ isCompleted = false }) => {
  const [isEntered, setIsEntered] = useRecoilState(IsEnteredAtom);
  const progress = useProgress();

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === "Enter" && progress.progress === 100) {
        setIsEntered(true);
      }
    };
    window.addEventListener("keydown", handleKeyDown);

    return () => {
      window.removeEventListener("keydown", handleKeyDown);
    };
  }, [setIsEntered, progress]);

  if (isEntered) return null;
  return (
    <Html center>
      <BlurredBackground />
      <Container>
        <Text>크롬 설정 - 시스템 - 그래픽 가속을 켜주세요</Text>
        <ProgressBar>{isCompleted ? 100 : progress.progress}%</ProgressBar>
        <EnterBtn
          onClick={() => {
            setIsEntered(true);
          }}
          disabled={progress.progress !== 100}
        >
          Enter
        </EnterBtn>
      </Container>
    </Html>
  );
};

const blink = keyframes`
0%{
    opacity:1;
}
50%{
    opacity:0
}
100%{
    opacity:1;
}
`;

const BlurredBackground = styled.div`
  width: 400px;
  height: 400px;
  background-color: #ee5050;
  border-radius: 50%;
  filter: blur(200px);
`;
const Container = styled.div`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  gap: 30px;
`;
const ProgressBar = styled.div`
  font-size: 24px;
  color: #ccc;
  font-family: Rajdhani;
`;
const EnterBtn = styled.button`
  font-family: Rajdhani;
  animation: ${blink} 1.5s infinite;
  transition-duration: 0.4s;
  font-size: 16px;
  outline: none;
  border: 0.5px solid #999;
  padding: 8px 18px;
  background-color: transparent;
  color: #ccc;
  border-radius: 8px;
  cursor: pointer;
  &:hover {
    background-color: #ccc;
    color: #ee5050;
    animation: none;
  }
`;

const Text = styled.p`
  font-family: Rajdhani;
  width: 100vw;
  text-align: center;
  color: #eee;
  font-size: 30px;
`;
