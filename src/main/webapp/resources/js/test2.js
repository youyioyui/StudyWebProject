function submitReply() {
	const replyContent = document.getElementById('replyContent').value;
	const brdno = document.querySelector('.brdnohidden').value;

	fetch('/board/detail', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify({
			brdno: brdno,
			replyContent: replyContent
		})
	})
		.then(response => response.json())
		.then(data => {
			console.log('Success:', data);
			window.location.reload();
		})
		.catch((error) => {
			console.error('Error:', error);
		});
}
//댓글삭제
function deleteReply(a, b) {
	let reNo = a;
	let userNo = b;
	
	console.log(reNo);
	console.log(userNo);
	
fetch('/board/detail', {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify({
			reno: reNo,
			userno: userNo
		})
	})
		.then(response => response.json())
		.then(data => {
			console.log('Delete Success:', data);
			window.location.reload();
		})
		.catch((error) => {
			console.error('Delete Error:', error);
		});
}


function submitUserReply() {
	const userReplyContent = document.querySelector('#userReplyContent').value;
	const userno = document.querySelector('.usernoHidden').value;

	fetch('/user/detail', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: JSON.stringify({
			userno: userno,
			userReplyContent: userReplyContent
		})
	})
		.then(response => response.json())
		.then(data => {
			console.log('Success:', data);

		})
		.catch((error) => {
			console.error('Error:', error);
		});
	
}