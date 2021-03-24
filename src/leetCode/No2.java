package leetCode;

public class No2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resHead;
        ListNode p;

        // 进位
        int x = 0;
        // 临时计算结果
        int temp;

        // 第一个节点特殊考虑l
        resHead = new ListNode();
        temp = l1.val + l2.val + x;
        if (temp < 10) {
            resHead.val = temp;
            x = 0;
        } else {
            resHead.val = temp % 10;
            x = 1;
        }
        p = resHead;

        while (true) {
            if (l1.next == null) {
                while (l2.next != null) {
                    l2 = l2.next;

                    ListNode q = new ListNode();
                    temp = l2.val + x;
                    if (temp < 10) {
                        q.val = temp;
                        x = 0;
                    } else {
                        q.val = temp % 10;
                        x = 1;
                    }

                    p.next = q;
                    p = q;
                }
                if (x == 1) {
                    ListNode q = new ListNode();
                    q.val = 1;
                    p.next = q;
                }
                break;
            }
            if (l2.next == null) {
                while (l1.next != null) {
                    l1 = l1.next;

                    ListNode q = new ListNode();
                    temp = l1.val + x;
                    if (temp < 10) {
                        q.val = temp;
                        x = 0;
                    } else {
                        q.val = temp % 10;
                        x = 1;
                    }

                    p.next = q;
                    p = q;
                }
                if (x == 1) {
                    ListNode q = new ListNode();
                    q.val = 1;
                    p.next = q;
                }
                break;
            }

            l1 = l1.next;
            l2 = l2.next;

            ListNode q = new ListNode();
            temp = l1.val + l2.val + x;
            if (temp < 10) {
                q.val = temp;
                x = 0;
            } else {
                q.val = temp % 10;
                x = 1;
            }

            p.next = q;
            p = q;
        }
        return resHead;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
