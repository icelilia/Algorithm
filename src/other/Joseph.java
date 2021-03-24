package other;

public class Joseph {
    // 1 <= N
    // K <= N
    // 剩余 K - 1 人
    public int[] JosephLast(int N, int K) {
        Node head = init(N);
        int remainder = N;
        while (remainder >= K) {
            for (int i = 1; i <= K; i++) {
                head = head.next;
            }
            // 删除head的前一个节点
            Node q = head.last;
            Node p = q.last;
            p.next = head;
            head.last = p;
            remainder--;
        }
        int[] last = new int[K - 1];
        for (int i = 0; i < K - 1; i++) {
            last[i] = head.no;
            head = head.next;
        }
        return last;
    }

    private Node init(int N) {
        Node head = new Node(1);
        Node p = head;
        for (int i = 2; i <= N; i++) {
            Node q = new Node(i);
            p.next = q;
            q.last = p;
            p = q;
        }
        p.next = head;
        head.last = p;
        return head;
    }

    public static class Node {
        int no;
        Node last;
        Node next;

        public Node() {

        }

        public Node(int no) {
            this.no = no;
        }
    }
}
